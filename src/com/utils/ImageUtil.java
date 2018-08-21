package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import com.utils.ConnUtil;

public class ImageUtil {
	// ��ȡ����ͼƬ��ȡ������
	public static FileInputStream readImage(String path) throws IOException {		
		return new FileInputStream(new File(path));
		}
	
	// ��ȡ����ͼƬ��ȡ�����
	public static FileOutputStream readBin2Image(InputStream in, String targetPath) {
		File file = new File(targetPath);
		String path = targetPath.substring(0, targetPath.lastIndexOf("/"));
		if (!file.exists()) {
			new File(path).mkdir();
			}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			int len = 0;
			byte[] buf = new byte[1024];
			while ((len = in.read(buf)) != -1) {
				fos.write(buf, 0, len);
				}
			fos.flush();
			return fos;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
				}
		}
	
	// ��ͼƬ�������ݿ�
	public static void readImage2DB() {
		String path = "src/com/utils/img/15-��������Ӫ-PH.png";
		Connection conn = null;
		PreparedStatement ps = null;
		FileInputStream in = null;
		try {	
			// ��ȡ����ͼƬ��ȡ������
			in = ImageUtil.readImage(path);
			conn = ConnUtil.getConn();
			String sql = "UPDATE stations set ���±���Ա='lydia'";
			//String sql = "UPDATE stations set ph_fig=? where gid=21";
			ps = conn.prepareStatement(sql);			
			//ps.setBinaryStream(1, in, in.available());
			int count = ps.executeUpdate();
			if (count > 0) {
				System.out.println("����ɹ���");
				} else {
					System.out.println("����ʧ�ܣ�");
					}
			conn.close();
			ps.close();
			} 
		catch (Exception e) {
			e.printStackTrace();
			}
		}
	
	// ��ȡ���ݿ���ͼƬ
	public static Blob readDB2Image() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		InputStream in = null;
		Blob b = null;
		try {
			conn = ConnUtil.getConn();
			String sql = "select * from stations where gid =?";		
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 1);
			rs = ps.executeQuery();
			while(rs.next()){
				//
				b = rs.getBlob("ph_fig"); 
				in = b.getBinaryStream();
//				long size = b.length(); 
//				byte[] bs = b.getBytes(1, (int)size);  
				}
			conn.close();
			rs.close();
			return b;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}				
		}
	
	//����
	public static void main(String[] args) {
		readImage2DB();
/*		String b = readDB2Image().toString();
		System.out.println(b);*/
		}
}
