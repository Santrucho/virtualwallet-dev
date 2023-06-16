package com.santrucho.virtualwalletdev.repository

import android.util.Log
import com.santrucho.virtualwalletdev.data.local.UserDao
import com.santrucho.virtualwalletdev.data.model.User
import com.santrucho.virtualwalletdev.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultUserRepository @Inject constructor(private val userDao: UserDao) : UserRepository {
    override suspend fun createUser(user: User): Resource<User> {
        return withContext(Dispatchers.IO) {
            try {
                val existingUser = userDao.getUserData(user.username)
                val existingEmail = userDao.getUserByEmail(user.email)
                if (existingUser != null) {
                    Resource.Failure(Exception("El nombre de usuario esta ocupado"))
                } else if (existingEmail != null) {
                    Resource.Failure(Exception("Ya existe un usuario con ese email registrado"))
                }
                val userId = userDao.createUser(user)
                val userGenerated = user.copy(cbu = userId.toString())
                Resource.Success(userGenerated)
            } catch (e: Exception) {
                Resource.Failure(e)
            }
        }
    }

    override suspend fun loginUser(username: String, password: String): Resource<User?> {
        return withContext(Dispatchers.IO) {
            try {
                val userByUsername = userDao.getUserData(username)
                if (userByUsername != null && userByUsername.password == password) {
                    Resource.Success(userByUsername)
                } else {
                    Resource.Failure(Exception("No existe ningun usuario con esas credenciales"))
                }
            } catch (e: Exception) {
                Resource.Failure(e)
            }
        }
    }

    override suspend fun getUserData(username: String): Resource<User?> {
        return withContext(Dispatchers.IO) {
            try {
                val userBalance = userDao.getUserData(username)
                Resource.Success(userBalance)
            } catch (e: Exception) {
                Resource.Failure(e)
            }
        }
    }

    override suspend fun transferMoney(
        addresseeCbu: String,
        senderCbu: String,
        amount: Int?
    ): Resource<Int> {
        return withContext(Dispatchers.IO) {
            try {
                val addresseeUser = userDao.getUserByCbu(addresseeCbu)
                val senderUser = userDao.getUserByCbu(senderCbu)

                if (addresseeUser == null) {
                    Log.d("pppppppppppppppppppppppp", "ENTRA AQUI CBU NO EXISTENTE")
                    Resource.Failure(Exception("El cbu ingresado no pertenece a ningún usuario"))
                } else {
                    if (senderUser != null) {
                        if (amount != null && senderUser.balance >= amount) {
                            val decreaseBalance = userDao.decreaseBalance(senderCbu, amount)
                            userDao.increaseBalance(addresseeCbu, amount)
                            Resource.Success(decreaseBalance)
                        } else {
                            if (amount == null) {
                                Resource.Failure(Exception("La transferencia debe ser mayor a 0.0$"))
                            } else {
                                Resource.Failure(Exception("El saldo no es suficiente"))
                            }
                        }
                    } else {
                        Resource.Failure(Exception("Error al enviar el dinero"))
                    }
                }
            } catch (e: Exception) {
                Resource.Failure(e)
            }
        }
    }
}