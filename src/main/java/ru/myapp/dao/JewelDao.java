package ru.myapp.dao;

import java.util.List;
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
	//public static int JEWEL_COUNT; //счетчик для инициализации id - используется как jewel.setId(++JEWEL_COUNT) 
	/**
	 * основной шаблон JDBC, класс предоставляет
	*простой доступ к базе данных через JDBC и простые запросы с
	*индексированными параметрами
	 */
	private final JdbcTemplate jdbcTemplate;
	
	/** Устанавливает доступ к базе данных 
	@Autowired
	public void setDataSource(DataSource dataSource){
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}*/
	@Autowired
    public JewelDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
	@SuppressWarnings("deprecation")
	public Jewel findById(int id) {
		/** запрос к базе данных вернет массив записей с указанным id, затем в лямбда-выражении выполнится поиск одного элемента из списка
		 * он будет возвращен, либо будет возвращен null, если список окажется пустым*/
		return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Jewel.class))
                .stream().findAny().orElse(null);
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