package com.example.menuhomework.database

import com.example.menuhomework.database.dao.WeatherDao

// Вспомогательный класс, развязывающий зависимость между Room и RecyclerView
class WeatherSource(private val educationDao: WeatherDao) {
    // Буфер с данными: сюда будем подкачивать данные из БД
    var requests: List<Request> = educationDao.getAllRequests()


    // Загружаем студентов в буфер
    private fun loadRequests() {
        requests = educationDao.getAllRequests()
    }

    // Получаем количество записей
    val countRequests: Long
        get() = educationDao.getCountRequests()

    // Добавляем студента
    fun addRequest(request: Request) {
        val id = educationDao.insertRequest(request)
        loadRequests()
    }

    // Заменяем студента
    fun updateRequest(weather: Request) {
        educationDao.updateRequest(weather)
        loadRequests()
    }

    // Удаляем студента из базы
    fun removeRequest(id: Long) {
        educationDao.deleteRequestById(id)
        loadRequests()
    }

    // видаляємо всіх студентів
    fun clearRequests() {
        educationDao.deleteAll()
        loadRequests()
    }
}
