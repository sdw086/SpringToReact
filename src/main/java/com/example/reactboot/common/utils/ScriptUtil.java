package com.example.reactboot.common.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class ScriptUtil {
	public ScriptUtil() {
    }
    
    /**
     * 입력한 테스트 보여줌
     * @param response
     * @param text 택스트
     * @throws IOException
     */
    public static void showText(HttpServletResponse response, String text) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter output = response.getWriter();
        output.println(text);
        output.flush();
        output.close();
    }
    
    /**
     * 현재 창 당기(팝업)
     * @param response
     * @throws IOException
     */
    public static void alertGoParent(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter output = response.getWriter();
        output.println("<script type='text/javascript' >");
        output.println("window.close();");
        output.println("</script>");
        output.flush();
        output.close();
    }
    
    /**
     * 자식 창 닫고 부모창 url 이동
     * @param response
     * @param returnUrl 이동 url
     * @throws IOException
     */
    public static void alertGoParentUrl(HttpServletResponse response, String returnUrl) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter output = response.getWriter();
        output.println("<script type='text/javascript' >");
        output.println("window.opener.location.href='" + returnUrl + "';");
        output.println("window.close();");
        output.println("</script>");
        output.flush();
        output.close();
    }
    
    /**
     * 메시지 호출후 url 호출후 자식창 닫기
     * @param response
     * @param msg
     * @param returnUrl 부모창 이동 url
     * @throws IOException
     */
    public static void alertMsgGoParentUrl(HttpServletResponse response, String msg, String returnUrl) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter output = response.getWriter();
        output.println("<script type='text/javascript' >");
        output.println("alert('" + msg.replaceAll("\\'", "\\\"") + "');");
        output.println("window.opener.location.href='" + returnUrl + "';");
        output.println("window.close();");
        output.println("</script>");
        output.flush();
        output.close();
    }
    
    /**
     * 메시지 호출후 부모창 리로드 후 자식창 닫기
     * @param response
     * @param msg
     * @throws IOException
     */
    public static void alertMsgGoParentUrlReload(HttpServletResponse response, String msg) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter output = response.getWriter();
        output.println("<script type='text/javascript' >");
        output.println("alert('" + msg.replaceAll("\\'", "\\\"") + "');");
        output.println("window.opener.location.reload();");
        output.println("window.close();");
        output.println("</script>");
        output.flush();
        output.close();
    }
    
    /**
     * 메시지 호출 후 부모창에 스크립트function 실행 후 자식창 닫기
     * @param response
     * @param msg 메시지
     * @param strFunc
     * @throws IOException
     */
    public static void alertMsgGoParentFunc(HttpServletResponse response, String msg, String strFunc) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter output = response.getWriter();
        output.println("<script type='text/javascript' >");
        output.println("alert('" + msg.replaceAll("\\'", "\\\"") + "');");
        output.println("window.opener." + strFunc + ";");
        output.println("window.close();");
        output.println("</script>");
        output.flush();
        output.close();
    }
    
    /**
     * 컨펌창 호출
     * @param response
     * @param msg 메시지
     * @param yesUrl yes url
     * @param cancelUrl cancel url
     * @throws IOException
     */
    public static void alertMsgConfirmUrl(HttpServletResponse response, String msg, String yesUrl, String cancelUrl) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter output = response.getWriter();
        output.println("<script type='text/javascript' >");
        output.println("if(confirm('" + msg.replaceAll("\\'", "\\\"") + "')) {");
        output.println("window.location.href='" + yesUrl + "';");
        output.println("} else { ");
        output.println("window.location.href='" + cancelUrl + "';");
        output.println("}</script>");
        output.flush();
        output.close();
    }
    
    /**
     *
     * @param moveAction action 구분 (back, close, '') '' 공백일시 단순 얼럿 창작동
     * @param message string 얼럿 메세지
     * @return
     * @throws Exception
     */
    public static Object alertMsg(HttpServletResponse response, String message, String moveAction) throws Exception {
	    try {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter output = response.getWriter();
            output.println("<script type='text/javascript' charset='utf-8'>");
            
            if (!"".equals(message)) {
                output.println("alert('" + message.replaceAll("\\'", "\\\"") + "');");
            }
            
            if ("back".equals(moveAction)) {
                output.println("window.history.back();");
            } else if ("close".equals(moveAction)) {
                output.println("window.close();");
            } else if ("reload".equals(moveAction)) {
                output.println("window.location.reload();");
            }
            
            output.println("</script>");
            output.flush();
            output.close();
            return null;
	        
        } catch (Exception e) {
	        e.printStackTrace();
        }
	    
	    return null;
    }
    
    /**
     *
     * @param message string 얼럿 메시지
     * @param returnUrl 이동할 페이지 url 필수값
     * @return
     * @throws Exception
     */
    public static void alertMsgGoUrl(HttpServletResponse response, String message, String returnUrl, HttpServletRequest request) throws Exception {
	    try {
	        if ("".equals(returnUrl)) {
	            alertMsg(response           , "back", "returnUrl parameter is required");
            } else {
                response.setContentType("text/html;charset=utf-8");
                PrintWriter output          = response.getWriter();
                
                output.println("<form name='frmJavaFun' action='" + returnUrl.split("\\?")[0] + "' method='post' >");
                
                Enumeration params          = request.getParameterNames();
                while(params.hasMoreElements()) {
                    String name             = (String) params.nextElement();
                    output.println("<input type='hidden' name='" + name + "' value='" + StringUtil.convertSpChar(StringUtil.nvl(request.getParameter(name), "")) + "'/>");
                }
                
                output.println("</form>");
                
                output.println("<script type='text/javascript' charset='utf-8'>");
                
                if (!"".equals(message)) {
                    output.println("alert('" + message.replaceAll("\\'", "\\\"").replaceAll("\n", "\\\\n") + "');");
                }
                output.println("document.frmJavaFun.submit();");
                output.println("</script>");
                
                output.flush();
                output.close();
            }
        } catch (Exception e) {
	        e.printStackTrace();
        }
    }
    
    /**
     *
     * @param message 얼럿 메시지
     * @param returnUrl 이동할 페이지 url 필수값
     * @param addParams 추가적인 데이터 혹은 데이터 갱신을 위한 용도
     * @throws Exception
     */
    public static void alertMsgGoUrl(HttpServletResponse response, String message, String returnUrl, HttpServletRequest request, Map<String, Object> addParams) throws Exception {
	    try {
	        if ("".equals(returnUrl)) {
	            alertMsg(response                   , "back", "returnUrl parameter is required");
            } else {
                response.setContentType("text/html;charset=utf-8");
                PrintWriter output                  = response.getWriter();
                
                Map<String, Object> requestMap      = new HashMap<>();
                
                Enumeration params                  = request.getParameterNames();
                while(params.hasMoreElements()) {
                    String name                     = (String) params.nextElement();
                    requestMap.put(name             , StringUtil.convertSpChar(StringUtil.nvl(request.getParameter(name))));
                }
                
                if (StringUtil.isNotEmpty(addParams)) {
                    Set<String> keySet              = addParams.keySet();
                    Iterator iterator               = keySet.iterator();
        
                    while(iterator.hasNext()) {
                        String key                  = (String)iterator.next();
                        Object value                = addParams.get(key);
                        requestMap.put(key          , StringUtil.convertSpChar(StringUtil.nvl(value, "")));
                    }
                }
                
                output.println("<form name='frmJavaFun' action='" + returnUrl.split("\\?")[0] + "' method='post' >");
                
                if (StringUtil.isNotEmpty(requestMap)) {
                    Set<String> keySet              = requestMap.keySet();
                    Iterator iterator               = keySet.iterator();
                    
                    while (iterator.hasNext()) {
                        String key                  = (String)iterator.next();
                        Object value                = requestMap.get(key);
                        output.println("<input type='hidden' name='" + key + "' value='" + value + "'/>");
                    }
                }
                
                output.println("</form>");
                output.println("<script type='text/javascript' charset='utf-8'>");
                
                if (!"".equals(message)) {
                    output.println("alert('" + message.replaceAll("\\'", "\\\"").replaceAll("\n", "\\\\n") + "');");
                }
                
                output.println("document.frmJavaFun.submit();");
                output.println("</script>");
                
                output.flush();
                output.close();
            }
        } catch (Exception e) {
	        e.printStackTrace();
        }
    }
    
    /**
     *
     * @param message string 얼럿 메시지
     * @param returnUrl 이동할 페이지 url 필수값
     * @return
     * @throws Exception
     */
    public static void alertMsgGoUrlNoParams(HttpServletResponse response, String message, String returnUrl) throws Exception {
	    try {
	        if ("".equals(returnUrl)) {
	            alertMsg(response               , "back", "returnUrl parameter is required");
            } else {
                response.setContentType("text/html;charset=utf-8");
                PrintWriter output              = response.getWriter();
                
                output.println("<script type='text/javascript' charset='utf-8'>");
                if (!"".equals(message)) {
                    output.println("alert('" + message.replaceAll("\\'", "\\\"").replaceAll("\n", "\\\\n") + "');");
                }
                output.println("window.location.href='" + returnUrl + "';");
                output.println("</script>");
                
                output.flush();
                output.close();
            }
        } catch (Exception e) {
	        e.printStackTrace();
        }
    }
}
