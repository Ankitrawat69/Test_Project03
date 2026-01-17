package in.co.rays.project_3.model;

import java.util.List;
import in.co.rays.project_3.dto.SuperMarketDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface SuperMarketModelInt {
	
	public long add(SuperMarketDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(SuperMarketDTO dto)throws ApplicationException;
	public void update(SuperMarketDTO dto)throws ApplicationException,DuplicateRecordException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(SuperMarketDTO dto)throws ApplicationException;
	public List search(SuperMarketDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public SuperMarketDTO findByPK(long pk)throws ApplicationException;
	public SuperMarketDTO fingByName(String name)throws ApplicationException;

}
