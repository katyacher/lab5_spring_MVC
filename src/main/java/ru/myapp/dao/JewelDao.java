package ru.myapp.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.myapp.model.Jewel;

/**
 * Шаблон DAO (Data Accses Object) для сущности Jewel.
 * JdbcTemplate автоматически получает
 * соединение, создает запрос и выполняет его.
 * 
 * Внутри класс JdbcTemplate перехватывает все исключения SQLException и
 * преобразует обобщенное исключение SQLException в одно из более конкретных
 * исключений доступа к данным
 */

@Component
public class JewelDao {
	/**
	 * основной шаблон JDBC, класс предоставляет
	*простой доступ к базе данных через JDBC и простые запросы с
	*индексированными параметрами
	 */
	JdbcTemplate jdbcTemplate;
	
	/** Устанавливает доступ к базе данных */
	@Autowired
	public void setDataSource(DataSource dataSource){
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	/** Осуществляет запрос к БД по выборке всех записей таблицы jewel, сохраняет ответ от БД в список объектов класса- сущности jewel  */
	public List<Jewel> findAll(){
		List<Jewel> jewels = jdbcTemplate.query("select * from jewel", new BeanPropertyRowMapper<>(Jewel.class));
		return jewels;
	}
	/** Выполняет запрос к БД для вставки новой записи в таблицу, данные для записи хранятся в переданном объекте.  */
	public int insert(Jewel jewel){
		return jdbcTemplate.update("insert into jewel " + "(id, size, price, type, metal, stone)" + "values (?,?,?,?,?,?)",
		new Object[] {
				jewel.getId(), jewel.getSize(),
				jewel.getPrice(), jewel.getType(), jewel.getMetal(), jewel.getStone()
		});
	}
	/** Выполняет запрос к базе данных с поиском записи по данному id */ 
	public List<Jewel> findById(int id) {
		List<Jewel> jewels = jdbcTemplate.query("select * from jewel where id=?", new BeanPropertyRowMapper<>(Jewel.class), id);
		return jewels;
	}
	
	/** Выполняетс запрос БД для редактирования записи по идентификатору */
	public void update(int id, Jewel updatedJewel) {
		jdbcTemplate.update("UPDATE jewel SET size=?, price=?, type=?, metal=?, stone=? WHERE id=?", updatedJewel.getSize(),
				updatedJewel.getPrice(), updatedJewel.getType(), updatedJewel.getMetal(), updatedJewel.getStone(), id);
	}
	/** Запрос удаления записи из таблицы по идентификатору */
	public void delete(int id) {
		jdbcTemplate.update("DELETE FROM jewel WHERE id=?", id);
	}
	/** Выполняется запрос к БД, где осуществляется выборка всех записей, выполняющих условие price <= upToPrice*/
	public List<Jewel> searchUpTo(int upToPrice){
		List<Jewel> jewels = jdbcTemplate.query("select * from jewel WHERE price <= ?", new BeanPropertyRowMapper<>(Jewel.class), upToPrice);
		return jewels;
	}
}