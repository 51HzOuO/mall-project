import mall.bean.FurnExample
import mall.mapper.FurnMapper
import org.apache.ibatis.io.Resources
import org.apache.ibatis.session.SqlSessionFactoryBuilder

fun main() {
    val sqlSessionFactory = SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"))
    sqlSessionFactory.openSession().use { session ->
        val mapper = session.getMapper(FurnMapper::class.java)
        val selectByExample = mapper.selectByExample(FurnExample().apply {
            createCriteria().andIdIsNull()
        })
        println(selectByExample)
        session.commit()
    }
}
