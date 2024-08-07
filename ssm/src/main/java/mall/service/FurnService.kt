package mall.service

import mall.bean.Furn


interface FurnService {
    fun save(furn: Furn): Boolean

    fun getAllFurn(): List<Furn>

    fun deleteById(id: Int): Boolean

    fun updateFurn(furn: Furn): Boolean
}
