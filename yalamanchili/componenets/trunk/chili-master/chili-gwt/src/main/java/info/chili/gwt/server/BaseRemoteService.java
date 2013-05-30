package info.chili.gwt.server;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public abstract class BaseRemoteService extends RemoteServiceServlet implements
        ServletContextAware {

    private static final long serialVersionUID = 7919984654320520594L;
    private ServletContext servletContext;

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        doPost(request, response);
        return null;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }
}