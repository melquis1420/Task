package com.example.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.service.HeaderModel
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.listener.ValidationListener
import com.example.tasks.service.repository.PersonRepository
import com.example.tasks.service.repository.local.SecurityPreferences

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val mPersonRepository = PersonRepository(application)
    private val mSharedPreferences = SecurityPreferences(application)

    private val mLogin = MutableLiveData<ValidationListener>()
    var login: LiveData<ValidationListener> = mLogin

    private val mLoggedUser = MutableLiveData<Boolean>()
    var loggedUser: LiveData<Boolean> = mLoggedUser

    /**
     * Sign in using API
     */
    fun doLogin(email: String, password: String) {
        mPersonRepository.login(email, password, object : APIListener {
            override fun onSuccess(model: HeaderModel) {
                /**
                 * Using SharedPreferences to save token, personKey and name
                 */
                mSharedPreferences.store(TaskConstants.SHARED.TOKEN_KEY, model.token)
                mSharedPreferences.store(TaskConstants.SHARED.PERSON_KEY, model.personKey)
                mSharedPreferences.store(TaskConstants.SHARED.PERSON_NAME, model.name)

                mLogin.value = ValidationListener()
            }

            override fun onFailure(str: String) {
                mLogin.value = ValidationListener(str)
            }

        })
    }

    /**
     * Checks if user is logged in
     */
    fun verifyLoggedUser() {

        val token = mSharedPreferences.get(TaskConstants.SHARED.TOKEN_KEY)
        val person = mSharedPreferences.get(TaskConstants.SHARED.PERSON_KEY)

        val logged = (token != "" && person != "")
        mLoggedUser.value = logged


    }

}