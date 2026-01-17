package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.SuperMarketDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class SuperMarketModelHibImp implements SuperMarketModelInt {

	@Override
	public long add(SuperMarketDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in SuperMarket Add " + e.getMessage());
		} finally {
			session.close();
		}
		return dto.getId();
	}

	@Override
	public void delete(SuperMarketDTO dto) throws ApplicationException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in SuperMarket Delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void update(SuperMarketDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(dto);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in SuperMarket Update " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(SuperMarketDTO.class);
			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize);
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in SuperMarket List");
		} finally {
			session.close();
		}
		return list;
	}

	@Override
	public List search(SuperMarketDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public List search(SuperMarketDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(SuperMarketDTO.class);

			if (dto.getId() != null && dto.getId() > 0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}

			if (dto.getProductName() != null && dto.getProductName().length() > 0) {
				criteria.add(Restrictions.like("ProductName", dto.getProductName() + "%"));
			}

			if (dto.getAvailable() != null && dto.getAvailable().length() > 0) {
				criteria.add(Restrictions.like("Available", dto.getAvailable() + "%"));
			}

			if (dto.getPrice() != null && dto.getPrice().length() > 0) {
				criteria.add(Restrictions.like("Price", dto.getPrice() + "%"));
			}

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in SuperMarket Search");
		} finally {
			session.close();
		}
		return list;
	}

	@Override
	public SuperMarketDTO findByPK(long pk) throws ApplicationException {
		Session session = null;
		SuperMarketDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (SuperMarketDTO) session.get(SuperMarketDTO.class, pk);
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in SuperMarket findByPK");
		} finally {
			session.close();
		}
		return dto;
	}

	@Override
	public SuperMarketDTO fingByName(String name) throws ApplicationException {
		Session session = null;
		SuperMarketDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(SuperMarketDTO.class);
			criteria.add(Restrictions.eq("ProductName", name));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (SuperMarketDTO) list.get(0);
			}
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in SuperMarket findByName " + e.getMessage());
		} finally {
			session.close();
		}
		return dto;
	}
}
