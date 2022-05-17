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

    private fun saveRequests(req: List<Request>){
        for(x in req){
            educationDao.insertRequest(x)
        }
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

    fun sortByName(isAsc : Int){
        requests = educationDao.getAllSortedByName(isAsc)
        educationDao.deleteAll()
        saveRequests(requests)
    }

    fun sortByDate(isAsc : Int){
        requests = educationDao.getAllSortedByDate(isAsc)
        educationDao.deleteAll()
        saveRequests(requests)
    }
}
