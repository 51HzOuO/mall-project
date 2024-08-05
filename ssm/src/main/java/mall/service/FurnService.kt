package mall.service

import mall.bean.Furn


interface FurnService {
    fun save(furn: Furn): Boolean

    fun getAllFurn(): List<Furn>
}
