package android.example.remote

interface IResponse<T> {

    fun toDomainObject(): T

}