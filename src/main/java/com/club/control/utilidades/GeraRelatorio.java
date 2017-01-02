/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.control.utilidades;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.*;
import javax.servlet.*;
import javax.servlet.http.*;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.query.JRJpaQueryExecuterFactory;

public class GeraRelatorio extends HttpServlet {
  protected void processRequest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException
 {
    ServletContext context = this.getServletConfig().getServletContext();
    // obtém o arquivo compilado .jasper
    File relatorioJasper = new
       File(context.getRealPath("/Reportes/informePorSector.jasper"));

    // parâmetros, se houver
    Map parametros = new HashMap();

    // Busca dados usando JPA e direciona a saída do relatório para um stream
    byte[] bytes = null;
    EntityManagerFactory emf =
       Persistence.createEntityManagerFactory("ClubSarandiPU");
    EntityManager em = emf.createEntityManager();
    try {
      parametros.put(JRJpaQueryExecuterFactory.PARAMETER_JPA_ENTITY_MANAGER, em);
      bytes =
          JasperRunManager.runReportToPdf(relatorioJasper.getPath(),parametros);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (em.isOpen()) em.close();
      if (emf.isOpen()) emf.close();
    }
    if (bytes != null && bytes.length > 0) {
      // Envia o relatório em formato PDF para o browser
      response.setContentType("application/pdf");
      response.setContentLength(bytes.length);
      ServletOutputStream ouputStream = response.getOutputStream();
      ouputStream.write(bytes, 0, bytes.length);
      ouputStream.flush();
      ouputStream.close();
    }
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException
  {
    processRequest(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException
  {
    processRequest(request, response);
  }
}


