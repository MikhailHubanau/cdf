/*!
 * Copyright 2002 - 2015 Webdetails, a Pentaho company.  All rights reserved.
 *
 * This software was developed by Webdetails and is provided under the terms
 * of the Mozilla Public License, Version 2.0, or any later version. You may not use
 * this file except in compliance with the license. If you need a copy of the license,
 * please go to  http://mozilla.org/MPL/2.0/. The Initial Developer is Webdetails.
 *
 * Software distributed under the Mozilla Public License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to
 * the license for the specific language governing your rights and limitations.
 */

package org.pentaho.cdf.views;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.cdf.util.Parameter;
import org.pentaho.platform.engine.core.system.PentahoSessionHolder;

import pt.webdetails.cpf.utils.PluginIOUtils;

@Path( "/pentaho-cdf/api/views" )
public class ViewsApi {

  private static final Log logger = LogFactory.getLog( ViewsApi.class );

  @GET
  @Path( "/" )
  @Consumes( { APPLICATION_JSON } )
  @Produces( APPLICATION_JSON )
  public void listViews( @Context HttpServletResponse servletResponse, @Context HttpServletRequest servletRequest ) {
    try {
      PluginIOUtils.writeOutAndFlush( servletResponse.getOutputStream(),
          ViewsEngine.getInstance().listViews( getUserName() ).toString( 2 ) );
    } catch ( Exception ex ) {
      logger.error( "Error listing views", ex );
    }
  }

  @PUT
  @Path( "/{name}" )
  @Consumes( { APPLICATION_JSON } )
  @Produces( APPLICATION_JSON )
  public void saveView( @DefaultValue( "" ) @PathParam( Parameter.NAME ) String name,
                        @DefaultValue( "" ) @FormParam( Parameter.VIEW ) String view,
                        @Context HttpServletResponse servletResponse, @Context HttpServletRequest servletRequest ) {
    try {
      PluginIOUtils.writeOutAndFlush( servletResponse.getOutputStream(),
          ViewsEngine.getInstance().saveView( name, view, getUserName() ).toString( 2 ) );
    } catch ( Exception ex ) {
      logger.error( "Error saving view '" + view + "'", ex );
    }
  }

  @DELETE
  @Path( "/{name}" )
  @Consumes( { APPLICATION_JSON } )
  @Produces( APPLICATION_JSON )
  public void deleteView( @DefaultValue( "" ) @PathParam( Parameter.NAME ) String name,
                          @Context HttpServletResponse servletResponse, @Context HttpServletRequest servletRequest ) {
    try {
      PluginIOUtils.writeOutAndFlush( servletResponse.getOutputStream(),
          ViewsEngine.getInstance().deleteView( name, getUserName() ).toString( 2 ) );
    } catch ( Exception ex ) {
      logger.error( "Error deleting view '" + name + "'", ex );
    }
  }

  @GET
  @Path( "/{name}" )
  @Consumes( { APPLICATION_JSON } )
  @Produces( APPLICATION_JSON )
  public void getView( @DefaultValue( "" ) @PathParam( Parameter.NAME ) String name,
                       @Context HttpServletResponse servletResponse, @Context HttpServletRequest servletRequest ) {
    try {
      PluginIOUtils.writeOutAndFlush( servletResponse.getOutputStream(),
          ViewsEngine.getInstance().getView( name, getUserName() ).toString( 2 ) );
    } catch ( Exception ex ) {
      logger.error( "Error getting view '" + name + "'", ex );
    }
  }

  private String getUserName() {
    return PentahoSessionHolder.getSession().getName();
  }
}
