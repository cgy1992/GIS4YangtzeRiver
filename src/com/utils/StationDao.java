package com.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Date;

import com.utils.Station;
import com.utils.ConnUtil;

public class StationDao {
	public static Connection conn = null;
	public static String sql = null;
	public static PreparedStatement ptmt = null;
	public static ResultSet rs = null;
	
	/** ��ȡ����վ��geometry **/
	public static String getStationGeom(){
    	try {
        //��ȡ����
        conn = ConnUtil.getConn();
        //sql, ÿ�мӿո�
        sql = "SELECT ST_AsGeoJSON(geom) from stations";
        //Ԥ����SQL������sqlִ��
        ptmt = conn.prepareStatement(sql);
        //ִ��
        rs = ptmt.executeQuery();
        //����λ��
        String json="{\"type\":\"FeatureCollection\",\"crs\":{\"type\":\"name\",\"properties\":{\"name\":\"EPSG:4326\"}},\"features\":[";
        while(rs.next()){
        	String line = "{\"type\":\"Feature\","+"\"geometry\":"+rs.getString(1)
        			+"},";
        	json=json + line;
        }
        json=json.substring(0, json.length()-1);
        json=json+"]}";
        rs.close();
        conn.close();
        return json;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
            }
    }
	
	/** ��ȡ����ͼ **/
	public static String getHeatmap1(){
    	try {
        //��ȡ����
        conn = ConnUtil.getConn();
        //sql, ÿ�мӿո�
        sql = "SELECT ST_AsGeoJSON(geom) from stations where ����ˮ��=?";
        //Ԥ����SQL������sqlִ��
        ptmt = conn.prepareStatement(sql);
        //ִ��
        ptmt.setString(1, "һ��ˮ��");
        rs = ptmt.executeQuery();
        //����λ��
        String json="{\"type\":\"FeatureCollection\",\"crs\":{\"type\":\"name\",\"properties\":{\"name\":\"EPSG:4326\"}},\"features\":[";
        while(rs.next()){
        	String line = "{\"type\":\"Feature\","+"\"geometry\":"+rs.getString(1)
        			+"},";
        	json=json + line;
        }
        json=json.substring(0, json.length()-1);
        json=json+"]}";
        rs.close();
        conn.close();
        return json;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
            }
    }
	public static String getHeatmap2(){
    	try {
        //��ȡ����
        conn = ConnUtil.getConn();
        //sql, ÿ�мӿո�
        sql = "SELECT ST_AsGeoJSON(geom) from stations where ����ˮ��=?";
        //Ԥ����SQL������sqlִ��
        ptmt = conn.prepareStatement(sql);
        //ִ��
        ptmt.setString(1, "����ˮ��");
        rs = ptmt.executeQuery();
        //����λ��
        String json="{\"type\":\"FeatureCollection\",\"crs\":{\"type\":\"name\",\"properties\":{\"name\":\"EPSG:4326\"}},\"features\":[";
        while(rs.next()){
        	String line = "{\"type\":\"Feature\","+"\"geometry\":"+rs.getString(1)
        			+"},";
        	json=json + line;
        }
        json=json.substring(0, json.length()-1);
        json=json+"]}";
        rs.close();
        conn.close();
        return json;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
            }
    }
	public static String getHeatmap3(){
    	try {
        //��ȡ����
        conn = ConnUtil.getConn();
        //sql, ÿ�мӿո�
        sql = "SELECT ST_AsGeoJSON(geom) from stations where ����ˮ��=?";
        //Ԥ����SQL������sqlִ��
        ptmt = conn.prepareStatement(sql);
        //ִ��
        ptmt.setString(1, "����ˮ��");
        rs = ptmt.executeQuery();
        //����λ��
        String json="{\"type\":\"FeatureCollection\",\"crs\":{\"type\":\"name\",\"properties\":{\"name\":\"EPSG:4326\"}},\"features\":[";
        while(rs.next()){
        	String line = "{\"type\":\"Feature\","+"\"geometry\":"+rs.getString(1)
        			+"},";
        	json=json + line;
        }
        json=json.substring(0, json.length()-1);
        json=json+"]}";
        rs.close();
        conn.close();
        return json;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
            }
    }
	
	/** ��ѯ�����ݵ�λ���ƣ� **/
	public static Station getQueryResult(String value){
    	try {
	        //��ȡ����
	        conn = ConnUtil.getConn();
	        //sql, ÿ�мӿո�
	        sql = "select * from stations where ��λ����=?";
	        //Ԥ����SQL������sqlִ��
	        ptmt = conn.prepareStatement(sql);
	        //ִ��
	        ptmt.setString(1, value);
	        rs = ptmt.executeQuery();
	        //��������
	        Station s = new Station();
	        while(rs.next()){
	            s.setId(rs.getInt("gid"));
	            s.setStationName(rs.getString("��λ����"));
	            s.setRiverName(rs.getString("��������"));
	            s.setSection(rs.getString("�������"));
	            s.setPH(rs.getBigDecimal("ph"));
	            s.setDO(rs.getBigDecimal("do_mg_l_"));
	            s.setCODMn(rs.getBigDecimal("codmn_mg_l"));
	            s.setNH3_N(rs.getBigDecimal("nh3_n_mg_l"));
	            s.setQualityThisWeek(rs.getString("����ˮ��"));
	            s.setQualityLastWeek(rs.getString("����ˮ��"));
	            s.setMainPollutant(rs.getString("��Ҫ��Ⱦָ��"));
	        }
	        rs.close();
	        conn.close();
	        return s;
        } catch (SQLException e) {
        	e.printStackTrace();
        	return null;
        	}
    }
	
	/** �����ѯ **/
	public static Station getClickResult(String coordinate){
    	try {
	        //��ȡ����
	        conn = ConnUtil.getConn();
	        //sql, ÿ�мӿո�	        
	        sql = "select * from stations where ST_Contains(ST_Buffer(geom,0.05),ST_PointFromText(?))";
	        //Ԥ����SQL������sqlִ��
	        ptmt = conn.prepareStatement(sql);
	        //ִ��
	        String point = "POINT(";
	        int split = coordinate.indexOf(",");
	        point = point + coordinate.subSequence(0, split-1) + " " + 
	        		coordinate.substring(split+1) + ")";	        
	        ptmt.setString(1,point);
	        rs = ptmt.executeQuery();
	        //��������
	        Station s = new Station();
	        while(rs.next()){
	            s.setId(rs.getInt("gid"));
	            s.setStationName(rs.getString("��λ����"));
	            s.setRiverName(rs.getString("��������"));
	            s.setSection(rs.getString("�������"));
	            s.setPH(rs.getBigDecimal("ph"));
	            s.setDO(rs.getBigDecimal("do_mg_l_"));
	            s.setCODMn(rs.getBigDecimal("codmn_mg_l"));
	            s.setNH3_N(rs.getBigDecimal("nh3_n_mg_l"));
	            s.setQualityThisWeek(rs.getString("����ˮ��"));
	            s.setQualityLastWeek(rs.getString("����ˮ��"));
	            s.setMainPollutant(rs.getString("��Ҫ��Ⱦָ��"));
	        }
	        rs.close();
	        conn.close();
	        return s;
        } catch (SQLException e) {
        	e.printStackTrace();
        	return null;
        	}
    }
	  
	/** ���Ӽ�¼ **/
    public static int addStation(Station s){
    	try {
    		//��ȡ����
            conn = ConnUtil.getConn();
            //sql
        	sql = "INSERT INTO stations (gid,��λ����,��������,�������,ph,do_mg_l_,codmn_mg_l,nh3_n_mg_l,����ˮ��,����ˮ��,��Ҫ��Ⱦָ��,geom,���±���Ա,��������Ա,������ʱ��,���±�ʱ��)"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,"
	        		+ "ST_PointFromText(?),?,?,?,?)";
	        //Ԥ����
	        ptmt = conn.prepareStatement(sql); //Ԥ����SQL������sqlִ��
	        //����id
	        int id = 50;
	        Statement st = conn.createStatement();
	        ResultSet res = st.executeQuery("select count(*) from stations");
	        if (res.next())
	              id = res.getInt(1) + 1;
	        res.close();
	        //����ռ���Ϣ�������
	        String point = "POINT(";
	        if(s.getCoordinate()!=null) {
		        int split = s.getCoordinate().indexOf(",");
		        point = point + s.getCoordinate().subSequence(0, split-1) + " " + 
		        		s.getCoordinate().substring(split+1) + ")";
	        }
	        //��ȡ��ǰʱ��
	        Date date = null;
	        res = st.executeQuery("select current_date");
	        if (res.next())
	        	date = res.getDate(1);
	        res.close();	        
	        //����
	        ptmt.setInt(1, id);
	        ptmt.setString(2, s.getStationName());
	        ptmt.setString(3, s.getRiverName());
	        ptmt.setString(4, s.getSection());
	        ptmt.setBigDecimal(5, s.getPH());
	        ptmt.setBigDecimal(6, s.getDO());
	        ptmt.setBigDecimal(7, s.getCODMn());
	        ptmt.setBigDecimal(8, s.getNH3_N());
	        ptmt.setString(9, s.getQualityThisWeek());
	        ptmt.setString(10, s.getQualityLastWeek());
	        ptmt.setString(11, s.getMainPollutant());
	        ptmt.setString(12, point);
	        ptmt.setString(13, s.getUpdateUser());
	        ptmt.setString(14, s.getCreateUser()); 
	        ptmt.setDate(15, date);
	        ptmt.setDate(16, date);	 
	        //ִ��
			ptmt.execute();
	        conn.close();
	        return 1;
	        } catch (SQLException e) {
	        	e.printStackTrace();
	        	return 0;
	        	}
    	}
    
    /** �޸��ֶ�ֵ **/
    public static int updateStationSN(String StationName, String FieldValue, String UpdateUser) {
    	try {
    		//��ȡ����
    		conn = ConnUtil.getConn();
    		//sql, ÿ�мӿո�
    		sql = "UPDATE stations set ��λ����=?, ���±���Ա=?, ���±�ʱ��=? where ��λ����=?";
    		//Ԥ����
    		ptmt = conn.prepareStatement(sql); //Ԥ����SQL������sqlִ��
	        //��ȡ��ǰʱ��
    		Date date = null;
    		Statement st = conn.createStatement();	        
	        ResultSet res = st.executeQuery("select current_date");
	        if (res.next())
	        	date = res.getDate(1);
	        res.close();
    		//����
	        ptmt.setString(1, FieldValue);
	        ptmt.setString(2, UpdateUser);
	        ptmt.setDate(3, date);
	        ptmt.setString(4, StationName);
            //ִ��
            ptmt.execute();
            conn.close();
            return 1;
            } catch (SQLException e) {
            	e.printStackTrace();
            	return 0;
            	}
    	}
    
    /** ɾ����¼ **/
    public static int delStation(String StationName) {
    	try {
    		//��ȡ����
    	    conn = ConnUtil.getConn();
            //sql, ÿ�мӿո�
            sql = "delete from stations where ��λ����=?";
            //Ԥ����SQL������sqlִ��
            ptmt = conn.prepareStatement(sql);
            //����
            ptmt.setString(1, StationName);
            //ִ��
            ptmt.execute();
            conn.close();
            return 1;
            } catch (SQLException e) {
            	e.printStackTrace();
            	return 0;
            	}
    	}
   
    public static void main(String[] args) { 
/*    	Station s = StationDao.getClickResult("102.013533,26.887");
    	s.setCoordinate("102.013533,26.887");
    	s.setPHFigPath("src/com/utils/img/15-��������Ӫ-PH.png");*/
    	//int a = updateStationS("��","��λ����","s","d");
    	//int a = addStation(s);
    	//System.out.println(a);
    }
}
