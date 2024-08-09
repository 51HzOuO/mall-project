package mall.service

import mall.bean.Furn
import mall.bean.FurnExample
import mall.mapper.FurnMapper
import org.mybatis.spring.SqlSessionTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class FurnServiceImpl(private val sqlSessionTemplate: SqlSessionTemplate) : FurnService {

    @Transactional
    override fun save(furn: Furn): Boolean {
        val mapper = sqlSessionTemplate.getMapper(FurnMapper::class.java)
        furn.id = null
        furn.sales = null
        return mapper.insertSelective(furn) == 1
    }

    override fun getAllFurn(): List<Furn> {
        val mapper = sqlSessionTemplate.getMapper(FurnMapper::class.java)
        return mapper.selectByExample(FurnExample())
    }

    override fun deleteById(id: Int): Boolean {
        val mapper = sqlSessionTemplate.getMapper(FurnMapper::class.java)
        return mapper.deleteByPrimaryKey(id) == 1
    }

    override fun updateFurn(furn: Furn): Boolean {
        val mapper = sqlSessionTemplate.getMapper(FurnMapper::class.java)
        return mapper.updateByPrimaryKeySelective(furn) == 1
    }

    override fun getFurnWithQuery(query: String): List<Furn> {
        val mapper = sqlSessionTemplate.getMapper(FurnMapper::class.java)
        return mapper.selectByExample(FurnExample().apply {
            val criteria = this.createCriteria()
            criteria.andNameLike("%${query}%")
            or().andCompanyLike("%${query}%")
        })
    }
}
