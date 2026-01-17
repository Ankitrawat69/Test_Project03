package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.SuperMarketDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

public class SuperMarketModelJDBCImp implements SuperMarketModelInt {

	private static Logger log = Logger.getLogger(SuperMarketModelJDBCImp.class);

	/* ===================== NEXT PK ===================== */
	public long nextPK() throws DatabaseException {
		long pk = 0;
		Connection con = null;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT MAX(ID) FROM ST_SUPERMARKET");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getLong(1);
			}
		} catch (Exception e) {
			log.error("Database Exception", e);
			throw new DatabaseException("Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		return pk + 1;
	}

	/* ===================== ADD ===================== */
	@Override
	public long add(SuperMarketDTO dto) throws ApplicationException, DuplicateRecordException {
		Connection con = null;
		long pk = 0;

		try {
			pk = nextPK();
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement(
				"INSERT INTO ST_SUPERMARKET VALUES(?,?,?,?,?,?,?,?)");

			ps.setLong(1, pk);
			ps.setString(2, dto.getProductName());
			ps.setLong(3, dto.getQuantity());
			ps.setString(4, dto.getAvailable());
			ps.setString(5, dto.getPrice());
			ps.setString(6, dto.getCreatedBy());
			ps.setString(7, dto.getModifiedBy());
			ps.setTimestamp(8, dto.getCreatedDatetime());

			ps.executeUpdate();
			ps.close();
			con.commit();

		} catch (Exception e) {
			log.error("Add Exception", e);
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback Exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in SuperMarket Add");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		return pk;
	}

	/* ===================== DELETE ===================== */
	@Override
	public void delete(SuperMarketDTO dto) throws ApplicationException {
		Connection con = null;

		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			PreparedStatement ps =
					con.prepareStatement("DELETE FROM ST_SUPERMARKET WHERE ID=?");
			ps.setLong(1, dto.getId());
			ps.executeUpdate();

			ps.close();
			con.commit();

		} catch (Exception e) {
			log.error("Delete Exception", e);
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback Exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in SuperMarket Delete");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
	}

	/* ===================== UPDATE ===================== */
	@Override
	public void update(SuperMarketDTO dto) throws ApplicationException {
		Connection con = null;

		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement(
				"UPDATE ST_SUPERMARKET SET PRODUCT_NAME=?, QUANTITY=?, AVAILABLE=?, PRICE=?, "
			  + "MODIFIED_BY=?, MODIFIED_DATETIME=? WHERE ID=?");

			ps.setString(1, dto.getProductName());
			ps.setLong(2, dto.getQuantity());
			ps.setString(3, dto.getAvailable());
			ps.setString(4, dto.getPrice());
			ps.setString(5, dto.getModifiedBy());
			ps.setTimestamp(6, dto.getModifiedDatetime());
			ps.setLong(7, dto.getId());

			ps.executeUpdate();
			ps.close();
			con.commit();

		} catch (Exception e) {
			log.error("Update Exception", e);
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback Exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in SuperMarket Update");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
	}

	/* ===================== FIND BY PK ===================== */
	@Override
	public SuperMarketDTO findByPK(long pk) throws ApplicationException {
		SuperMarketDTO dto = null;
		Connection con = null;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps =
					con.prepareStatement("SELECT * FROM ST_SUPERMARKET WHERE ID=?");
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				dto = new SuperMarketDTO();
				dto.setId(rs.getLong(1));
				dto.setProductName(rs.getString(2));
				dto.setQuantity(rs.getLong(3));
				dto.setAvailable(rs.getString(4));
				dto.setPrice(rs.getString(5));
			}
			rs.close();
			ps.close();

		} catch (Exception e) {
			log.error("FindByPK Exception", e);
			throw new ApplicationException("Exception in SuperMarket findByPK");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		return dto;
	}

	/* ===================== LIST ===================== */
	@Override
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {
		ArrayList<SuperMarketDTO> list = new ArrayList<>();
		Connection con = null;

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUPERMARKET");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				SuperMarketDTO dto = new SuperMarketDTO();
				dto.setId(rs.getLong(1));
				dto.setProductName(rs.getString(2));
				dto.setQuantity(rs.getLong(3));
				dto.setAvailable(rs.getString(4));
				dto.setPrice(rs.getString(5));
				list.add(dto);
			}
			rs.close();
			ps.close();

		} catch (Exception e) {
			log.error("List Exception", e);
			throw new ApplicationException("Exception in SuperMarket List");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		return list;
	}

	/* ===================== SEARCH ===================== */
	@Override
	public List search(SuperMarketDTO dto, int pageNo, int pageSize) throws ApplicationException {

		ArrayList<SuperMarketDTO> list = new ArrayList<>();
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUPERMARKET WHERE 1=1");

		if (dto != null) {
			if (dto.getId() > 0) {
				sql.append(" AND ID=" + dto.getId());
			}
			if (dto.getProductName() != null && dto.getProductName().length() > 0) {
				sql.append(" AND PRODUCT_NAME LIKE '" + dto.getProductName() + "%'");
			}
			if (dto.getAvailable() != null && dto.getAvailable().length() > 0) {
				sql.append(" AND AVAILABLE LIKE '" + dto.getAvailable() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		Connection con = null;
		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				SuperMarketDTO sm = new SuperMarketDTO();
				sm.setId(rs.getLong(1));
				sm.setProductName(rs.getString(2));
				sm.setQuantity(rs.getLong(3));
				sm.setAvailable(rs.getString(4));
				sm.setPrice(rs.getString(5));
				list.add(sm);
			}
			rs.close();
			ps.close();

		} catch (Exception e) {
			log.error("Search Exception", e);
			throw new ApplicationException("Exception in SuperMarket Search");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		return list;
	}

	@Override
	public List search(SuperMarketDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SuperMarketDTO fingByName(String name) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}
}
