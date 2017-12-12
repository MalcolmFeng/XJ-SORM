package creator;

/**
 * 封装了java文件（源代码）常用的操作。
 * @author Malcolm
 *
 */
public class XMLMapperCreatorSQLServer {
	
	public static String createXMLHeaderSrc() {
		
//		<?xml version="1.0" encoding="UTF-8"?>
//		<!DOCTYPE mapper
//		        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
//		        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
//				<mapper namespace="com.jiefupay.newplat.dao.IActionDao" >
		
		return null;
	}
	
	public static String createXMLFooterSrc() {
//		</mapper> 
		return null;
	}
	
	
	public static String createResultMapSrc() {
		
//		<resultMap id="action" type="com.jiefupay.newplat.bean.ActionBean" >
//	        <result column="id" property="id"/>
//	        <result column="uid" property="uid"/>
//	        <result column="uname" property="uname"/>
//	        <result column="uflag" property="uflag"/>
//	        <result column="typ" property="typ"/>
//	        <result column="ip" property="ip"/>
//	        <result column="tim" property="tim"/>
//	        <result column="content" property="content"/>
//	        <result column="realname" property="realname"/>
//	    </resultMap>
		
    		return null;
	}
	
	public static String createBASEALLCOLUMESrc() {
		
//		<sql id="Base_Column_List">
//    			id,uid,uname,uflag,typ,ip,tim,content
//		</sql>
		
    		return null;
	}
	
	
	public static String createAddSrc() {
		
//		<insert id="add" parameterType="Object" >
//			insert into [newdata_2014].[dbo].[kjpay_action] 
//			(uid,uname,uflag,typ,ip,tim,content)
//			values
//			(#{uid},#{uname},#{uflag},#{typ},#{ip},GetDate(),#{content})
//		</insert>
		
    		return null;
	}

	public static String createQueryRowsSrc() {
		
//		<select id="queryRows"  resultType="int" parameterType="Object">
//			SELECT count(id) from [newdata_2014].[dbo].[kjpay_action] where
//			tim BETWEEN #{start_time} and #{end_time}
//			<if test="uname!=null and uname!=''">
//	       		and uname like '%${uname}%'
//	        </if>
//	        <if test="ip!=null and ip!=''">
//	        	and ip like '%${ip}%'
//	        </if>
//	        <if test="typ!=null and typ!=''">
//	        	and typ like '%${typ}%'
//	        </if>
//	        <if test="content!=null and content!=''">
//	        	and content like '%${content}%'
//	        </if>
//		</select>
		
    		return null;
	}
	
	public static String createQueryListSrc() {
//		<select id="queryList" resultMap="action"  parameterType="Object">
//			select * from 
//			(
//			select row_number() over(order by action.id desc) as rowNum, action.id,action.uid,action.uname,action.uflag,action.typ,action.ip,action.tim,action.content,admin.realname as realname
//	        from [newdata_2014].[dbo].[kjpay_action] action left join [newdata_2014].[dbo].[kjpay_admin] admin on
//	        action.uname = admin.username
//	        where 
//	        tim BETWEEN #{bean.start_time} and #{bean.end_time}
//	       	<if test="bean.uname!=null and bean.uname!=''">
//	       		and uname like '%${bean.uname}%'
//	        </if>
//	        <if test="bean.ip!=null and bean.ip!=''">
//	        	and ip like '%${bean.ip}%'
//	        </if>
//	        <if test="bean.typ!=null and bean.typ!=''">
//	        	and typ like '%${bean.typ}%'
//	        </if>
//	        <if test="bean.content!=null and bean.content!=''">
//	        	and content like '%${bean.content}%'
//	        </if>
//	        ) 
//	        t where rowNum between #{start} and (#{start}+#{limit}-1)
//		</select>
		
    		return null;
	}
	
	
	
}
