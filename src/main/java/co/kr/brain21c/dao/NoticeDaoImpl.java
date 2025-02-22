package co.kr.brain21c.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import co.kr.brain21c.dto.board;
import co.kr.brain21c.paging.Criteria;

@Repository("NoticeDao")
public class NoticeDaoImpl implements NoticeDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int selectNoticeTotalCount(String search_key) throws Exception {
		String sql = "SELECT COUNT(*) FROM board WHERE gnb = 'A01' ";

		if (search_key != null) {
			sql += "AND title LIKE '%" + search_key + "%' ";
		}

		int count = jdbcTemplate.queryForObject(sql, Integer.class);

		return count;
	}

	@Override
	public ArrayList<board> getNoticeList(Criteria criteria, String search_key) throws Exception {

		int startPage = (criteria.getCurrentPageNo() - 1) * criteria.getRecordsPerPage();

		// String sql = "SELECT * FROM board WHERE gnb = 'A01' ORDER BY seq DESC LIMIT "
		// + startPage + ", " + criteria.getRecordsPerPage();

		String sql = "";
		if (search_key != null) {
			sql = "SELECT * FROM board WHERE gnb = 'A01' AND title LIKE '%" + search_key + "%' ORDER BY seq DESC LIMIT "
					+ startPage + ", " + criteria.getRecordsPerPage();
		} else {
			sql = "SELECT * FROM board WHERE gnb = 'A01' ORDER BY seq DESC LIMIT " + startPage + ", "
					+ criteria.getRecordsPerPage();
		}

		List<board> nList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(board.class));

		//nList.forEach(System.out::println);

		return (ArrayList<board>) nList;
	}

	@Override
	public board getNoticeView(int seq) throws Exception {
		String sql = "SELECT * FROM board WHERE gnb = 'A01' and seq='" + seq + "'";

		List<board> nList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(board.class));

		//System.out.println("===============");
		//System.out.println(nList.get(0));

		return nList.get(0);
	}

}
