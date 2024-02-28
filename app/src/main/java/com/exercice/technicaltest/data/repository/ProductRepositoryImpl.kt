package com.exercice.technicaltest.data.repository

import com.exercice.technicaltest.data.remote.RemoteApi
import com.exercice.technicaltest.data.local.dao.ProductDao
import com.exercice.technicaltest.di.IoDispatcher
import com.exercice.technicaltest.domain.repository.ProductRepository
import com.exercice.technicaltest.models.Product
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteApi: RemoteApi,
    private val productDao: ProductDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ProductRepository {

    override suspend fun addProduct(image: String): Flow<Result<Product>> {
        return flow {
            val products = productDao.getProducts()
            val size = if (products.isEmpty()) 1 else products.size + 1
            val product = remoteApi.getProductDetails(size)
            productDao.insert(
                Product(
                    product.id,
                    product.title,
                    product.description,
                    product.price,
                    product.discountPercentage,
                    product.rating,
                    product.stock,
                    product.brand,
                    product.category,
                    product.thumbnail
                )
            )
            emit(Result.success(product))
        }.catch { emit(Result.failure(it)) }
            .flowOn(ioDispatcher)
    }

    override suspend fun getProductDetails(productId: Int): Flow<Result<Product>> {
        return flow {
            emit(Result.success(productDao.getProductById(productId)))
        }
    }

    override fun getProducts(): Flow<Result<List<Product>>> {
        return flow {
            emit(Result.success(productDao.getProducts()))
        }
    }

}
