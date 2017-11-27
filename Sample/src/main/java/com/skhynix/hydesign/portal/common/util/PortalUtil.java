package com.skhynix.hydesign.portal.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Logger;
import com.jcraft.jsch.Session;

/**
 * 공통 util
 *
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 1.00
 * @created 2014. 12. 24.
 */
public class PortalUtil {

    /**
     * Default Constructor
     */
    public PortalUtil() {
        // Default Constructor
    }
    
	public static String clearXSS(String value) {
		if (value == null || value.trim().equals("")) {
			return "";
		}

		String returnValue = value;

		returnValue = returnValue.replaceAll("&", "&amp;");
		returnValue = returnValue.replaceAll("<", "&lt;");
		returnValue = returnValue.replaceAll(">", "&gt;");
		returnValue = returnValue.replaceAll("\"", "&#34;");
		returnValue = returnValue.replaceAll("\'", "&#39;");
		return returnValue;
	}

    /**
     * 오늘 기준으로 가감된 날짜를 format 에 맞게 변경하여 반환
     *
     * @param days 가감되는 날짜 예) 어제 : -1
     * @param format 날짜 형식 예) yyyyMMddHHmmss
     * @return 현재 시간을 format 에 맞게 변경한 문자열
     */
    public static String getDateFormat(int days, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);

        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DATE, days);

        Date date = cal.getTime();
        return formatter.format(date);
    }

    /**
     * 물리 파일명 생성 : yyyyMMddHHmmss + 6자리 랜덤숫자.확장자
     *
     * @param orgFileNm - 원본 파일명
     * @return 물리 파일명
     * @throws NoSuchAlgorithmException
     */
    public static String getPhyFileName(String orgFileNm) throws NoSuchAlgorithmException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        StringBuilder sb = new StringBuilder();
        SecureRandom sRandom = SecureRandom.getInstance("SHA1PRNG");
        sb.append(formatter.format(new Date()) + sRandom.nextInt(1000000));
        if (orgFileNm.lastIndexOf(".") > -1) {
            sb.append(orgFileNm.substring(orgFileNm.lastIndexOf(".")));
        }

        return sb.toString();
    }

    /**
     * Html 태그 적용방지 및 개행방지 처리
     *
     * @param src 원본 문자열
     * @return 처리 후 문자열
     */
    public static String escape(String src) {
        if (src == null) {
            return null;
        }

        src = src.replaceAll("&", "&amp;");

        // Html 태그 적용 방지
        src = src.replaceAll("<", "&lt;");
        src = src.replaceAll(">", "&gt;");

        // 개행방지 처리
        src = src.replaceAll(" ", "&nbsp;");
        src = src.replaceAll("\n", "<br />");

        return src;
    }

    /**
     * Html 태그 적용 및 개행 적용
     *
     * @param src 원본 문자열
     * @return 처리 후 문자열
     */
    public static String unescape(String src) {
        if (src == null) {
            return null;
        }

        // 개행 처리
        src = src.replaceAll("&nbsp;", " ");
        src = src.replaceAll("/\\\\xB6/", "\n");
        src = src.replaceAll("<br />", "\n");
        src = src.replaceAll("<br/>", "\n");

        // Html 태그 적용
        src = src.replaceAll("&gt;", ">");
        src = src.replaceAll("&lt;", "<");

        src = src.replaceAll("&amp;", "&");

        return src;
    }

    /**
     * 파일 크기 단위 변환
     *
     * @param fileSize 파일 크기
     * @return byte를 파일 크기에 따라 KB, MB, GB 중 하나로 변환
     */
    public static String getFileSizeConversion(long fileSize) {
        double quotient = fileSize; // 몫
        int loop = 0;

        // loop 의 수에 따라, KB/MB/GB 의 단위를 구분
        do {
            quotient = quotient / 1024;
            loop++;
        } while (quotient > 1024 && loop < 3);

        String unit = "KB";

        if (loop == 2) { // MB
            unit = "MB";
        } else if (loop > 2) { // GB
            unit = "GB";
        }

        DecimalFormat df = new DecimalFormat("#,###.##");

        return (df.format(Math.floor(quotient * 100d) / 100d) + unit);
    }

    /**
     * 파일 크기 천 단위마다 구분 기호 추가
     *
     * @param fileSize 파일 크기
     * @return 파일 크기를 천 단위마다 구분기호(,) 추가하여 반환
     */
    public static String getFileSizeThousandMark(long fileSize) {

        DecimalFormat df = new DecimalFormat("#,###");

        return (df.format(fileSize));
    }

    /**
     * 첨부파일 파일 복사(임시경로 -> 저장경로)
     *
     * @param tempPath 임시경로
     * @param attachFilePath 저장경로
     * @param fileList 첨부파일 리스트
     */
    public static Boolean attachFileCopy(String tempPath, String attachFilePath, List<Object> fileList) {

        File newsFileDir = new File(attachFilePath);
        try {
            if (!newsFileDir.exists()) {
                FileUtils.forceMkdir(newsFileDir);
            }

            Map<String, Object> fileMap;
            for (int i = 0, len = fileList.size(); i < len; i++) {
                fileMap = (Map<String, Object>)fileList.get(i);

                Path srcPath = Paths.get(tempPath + File.separatorChar + fileMap.get("filePhysicsNm"));
                Path destPath = Paths.get(attachFilePath + File.separatorChar + fileMap.get("filePhysicsNm"));

                Files.copy(srcPath, destPath);

            }
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    /**
     * 첨부파일 파일 삭제
     *
     * @param attachFilePath 첨부파일 업로드 파일 경로
     * @param fileList 첨부파일 리스트
     */
    public static void attachFileDelete(String attachFilePath, List<Object> fileList) {

        Map<String, Object> fileMap;

        for (int i = 0, len = fileList.size(); i < len; i++) {
            fileMap = (Map<String, Object>)fileList.get(i);
            File newsFile = new File(attachFilePath + File.separatorChar + fileMap.get("filePhysicsNm"));
            if (newsFile.exists()) {
                // 1. News 파일 삭제
                newsFile.delete();
            }
        }
    }

    /**
     * 웹에디터 내용 치환
     *
     * @param content 웹에디터 내용
     * @param url 이미지 보기 경로
     * @return Map 웹에디터 내용, 파일이름 목록
     */
    public static Map<String, Object> replaceWebEditorCont(String content, String url) {
        /**
         * <pre>
         * 1. News의 html 내용 중 img src 경로 변경
         * 2. img src의 파일이름 목록 생성
         * </pre>
         */

        Map<String, Object> webEditorContMap = new HashMap<String, Object>();

        // html 내용의 img src 경로 변경
        content = content.replaceAll("tempImage\\?", url);
        webEditorContMap.put("content", content);

        Pattern pattern = Pattern.compile("[0-9]{14,20}.(?i)[jp[e]?g|png|gif|bmp]{3,4}");
        Matcher match = pattern.matcher(content);

        List<Object> fileNameList = new ArrayList<Object>();

        // img src의 파일 이름 리스트에 추가
        while (match.find()) {
            fileNameList.add(match.group());
        }

        webEditorContMap.put("fileNameList", fileNameList);

        return webEditorContMap;
    }

    /**
     * 웹에디터 내용 파일 복사(임시경로 -> 저장경로)
     *
     * @param tempEditorPath 임시경로
     * @param saveEditorPath 저장 경로
     * @param fileNameList 파일이름 목록
     * @return Boolean 복사 여부
     */
    public static Boolean webEditorFileCopy(String tempEditorPath, String saveEditorPath, List<Object> fileNameList) {
        File saveEditorFileDir = new File(saveEditorPath);
        try {
            if (!saveEditorFileDir.exists()) {
                FileUtils.forceMkdir(saveEditorFileDir);
            }

            for (Object fileName : fileNameList) {

                File srcFile = new File(tempEditorPath + File.separatorChar + fileName);
                File destFile = new File(saveEditorPath + File.separatorChar + fileName);

                // 저장 경로에 파일이 없을 경우에만 복사
                if (!destFile.exists()) {
                    FileUtils.copyFile(srcFile, destFile);
                }
            }
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    /**
     * 웹에디터 내용 파일 삭제
     *
     * @param saveEditorPath 저장 경로
     * @param fileNameList 파일이름 목록
     */
    public static void webEditorFileDelete(String saveEditorPath, List<Object> fileNameList) {
        /**
         * <pre>
         * 1. 저장 경로의 파일 목록 조회
         * 2. 저장 경로의 파일 목록과 파라미터의 파일이름 목록 비교
         * 3. 파라미터의 파일이름 목록에 없는 저장 경로의 파일 삭제
         * </pre>
         */
        File saveEditorPathDir = new File(saveEditorPath);

        // 저장 경로의 파일 목록
        String[] saveEditorFileArray = saveEditorPathDir.list();

        if (!Arrays.equals(saveEditorFileArray, fileNameList.toArray()) && saveEditorFileArray != null) {
            for (String newsEditorFile : saveEditorFileArray) {
                // 파일이름 목록에 저장 경로의 파일이 없을 경우 저장 경로의 파일 삭제
                if (!fileNameList.contains(newsEditorFile)) {
                    File deleteFile = new File(saveEditorPath + File.separatorChar + newsEditorFile);
                    if (deleteFile.exists()) {
                        deleteFile.delete();
                    }
                }
            }
        }
    }

    /**
     * SSH 명령 실행
     *
     * @param host ssh호스트
     * @param user ssh계정명
     * @param password ssh계정암호
     * @param command ssh실행명령
     * @return 명령실행 성공하면 실행명령 리턴값, 실패하면 -1
     */
    public static String sshCommandExec(String host, String user, String password, String command) {

        String strRespon = "";

        try {
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");

            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.connect(30000);

            Channel channel = session.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec)channel).setErrStream(System.err, true);

            InputStream in = channel.getInputStream();
            channel.connect();

            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) {
                        break;
                    }
                    strRespon += new String(tmp, 0, i);
                }

                if (channel.isClosed()) {
                    if (in.available() > 0) {
                        continue;
                    }
                    break;
                }
                Thread.sleep(1000);
            }
            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            strRespon = "-1";
        }

        return strRespon;
    }

	/**
	 * InputStream 닫음
	 *
	 * @param is
	 */
	public static void safeCloseIn(InputStream in) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * OutputStream 닫음
	 *
	 * @param os
	 */
	public static void safeCloseOut(OutputStream out) {
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Null 처리
	 *
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static Object NVL(Object o1, Object o2) {
		if(o1 == null) return o2;
		else return o1;
	}


	/*
	public static Key getAESKey() throws Exception {
	    String iv;
	    Key keySpec;

	    String key = "1234567890123456";
	    iv = key.substring(0, 16);
	    byte[] keyBytes = new byte[16];
	    byte[] b = key.getBytes();

	    int len = b.length;
	    if (len > keyBytes.length) {
	       len = keyBytes.length;
	    }

	    System.arraycopy(b, 0, keyBytes, 0, len);
	    keySpec = new SecretKeySpec(keyBytes, "AES");
	    return keySpec;
	}
	*/

	// 암호화
	public static String encAES(String str) throws Exception {
//	    Key keySpec = getAESKey();
//	    Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
//	    String iv = "1234567890123456";
//	    c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
//	    byte[] encrypted = c.doFinal(str.getBytes());
//	    String enStr = new String(Base64.encodeBase64(encrypted));

	    BASE64Encoder base64Encoder = new BASE64Encoder();
	    String enStr = base64Encoder.encode(str.getBytes());

	    return enStr;
	}

	// 복호화
	public static String decAES(String enStr) throws Exception {
//	    Key keySpec = getAESKey();
//	    Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
//	    String iv = "1234567890123456";
//	    c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
//	    byte[] byteStr = Base64.decodeBase64(enStr.getBytes());
//	    String decStr = new String(c.doFinal(byteStr));

	    BASE64Decoder base64Decoder = new BASE64Decoder();
	    String decStr = new String(base64Decoder.decodeBuffer(enStr));

	    return decStr;
	}

	// 파일복사
	public static void copyDirectory(File sourcelocation , File targetdirectory) throws Exception {
		//디렉토리인 경우
        if (sourcelocation.isDirectory()) {
			// 복사될 Directory가 없으면 만듭니다.
			if (!targetdirectory.exists()) {
				targetdirectory.mkdir();
			}

			String[] children = sourcelocation.list();
			for (int i = 0; i < children.length; i++) {
				copyDirectory(new File(sourcelocation, children[i]), new File(targetdirectory, children[i]));
			}
        } else {
			// 파일인 경우
        	
        	InputStream in = null;
        	OutputStream out= null;
        	
        	try {
        		
        		in = new FileInputStream(sourcelocation);
        		out = new FileOutputStream(targetdirectory);

        		// Copy the bits from instream to outstream
        		byte[] buf = new byte[1024];
        		int len;
        		while ((len = in.read(buf)) > 0) {
        			out.write(buf, 0, len);
        		}

			} catch (FileNotFoundException e) {
				return ;
			} finally {
				if(in != null) safeInClose(in);
				if(out != null) safeOutClose(out);
			}
        }

		return ;
	}

	
	public static void safeInClose(InputStream in) {
	  if (in != null) {
	    try {
	    	in.close();
	    } catch (IOException e) {
		      e.printStackTrace();
	    }
	  }
	}

	public static void safeOutClose(OutputStream out) {
	  if(out != null) {
	    try {
	    	out.close();
	    } catch (IOException e) {
		      e.printStackTrace();
	    }
	  }
	}
	
}
