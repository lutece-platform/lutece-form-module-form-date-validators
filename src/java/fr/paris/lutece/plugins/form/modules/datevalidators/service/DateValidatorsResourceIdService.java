/*
 * Copyright (c) 2002-2013, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.form.modules.datevalidators.service;

import fr.paris.lutece.plugins.form.modules.datevalidators.business.Rule;
import fr.paris.lutece.portal.service.rbac.Permission;
import fr.paris.lutece.portal.service.rbac.ResourceIdService;
import fr.paris.lutece.portal.service.rbac.ResourceType;
import fr.paris.lutece.portal.service.rbac.ResourceTypeManager;
import fr.paris.lutece.util.ReferenceList;

import java.util.Locale;


/**
 * DateValidatorsResourceIdService
 */
public class DateValidatorsResourceIdService extends ResourceIdService
{
    public static final String PERMISSION_RULE_CREATE = "PERMISSION_RULE_CREATE";
    public static final String PERMISSION_RULE_MODIFY = "PERMISSION_RULE_MODIFY";
    public static final String PERMISSION_RULE_DELETE = "PERMISSION_RULE_DELETE";
    public static final String PERMISSION_DATE_CALCULATED_MANAGE = "PERMISSION_DATE_CALCULATED_MANAGE";
    public static final String PERMISSION_DATE_CALCULATED_CREATE = "PERMISSION_DATE_CALCULATED_CREATE";
    public static final String PERMISSION_DATE_CALCULATED_MODIFY = "PERMISSION_DATE_CALCULATED_MODIFY";
    public static final String PERMISSION_DATE_CALCULATED_DELETE = "PERMISSION_DATE_CALCULATED_DELETE";
    private static final String PROPERTY_LABEL_RESOURCE_TYPE = "module.form.datevalidators.permission.label.resource_type";
    private static final String PROPERTY_LABEL_PERMISSION_RULE_CREATE = "module.form.datevalidators.permission.rule.label.create";
    private static final String PROPERTY_LABEL_PERMISSION_RULE_MODIFY = "module.form.datevalidators.permission.rule.label.modify";
    private static final String PROPERTY_LABEL_PERMISSION_RULE_DELETE = "module.form.datevalidators.permission.rule.label.delete";
    private static final String PROPERTY_LABEL_PERMISSION_DATE_CALCULATED_MANAGE = "module.form.datevalidators.permission.dateCalculated.label.manage";
    private static final String PROPERTY_LABEL_PERMISSION_DATE_CALCULATED_CREATE = "module.form.datevalidators.permission.dateCalculated.label.create";
    private static final String PROPERTY_LABEL_PERMISSION_DATE_CALCULATED_MODIFY = "module.form.datevalidators.permission.dateCalculated.label.modify";
    private static final String PROPERTY_LABEL_PERMISSION_DATE_CALCULATED_DELETE = "module.form.datevalidators.permission.dateCalculated.label.delete";

    /**
     * Creates a new instance of DateValidatorsResourceIdService
     */
    public DateValidatorsResourceIdService(  )
    {
        setPluginName( DateValidatorsPlugin.PLUGIN_NAME );
    }

    /**
     * Initializes the service
     */
    public void register(  )
    {
        ResourceType rt = new ResourceType(  );
        rt.setResourceIdServiceClass( DateValidatorsResourceIdService.class.getName(  ) );
        rt.setPluginName( DateValidatorsPlugin.PLUGIN_NAME );
        rt.setResourceTypeKey( Rule.RESOURCE_TYPE );
        rt.setResourceTypeLabelKey( PROPERTY_LABEL_RESOURCE_TYPE );

        Permission p = new Permission(  );
        p.setPermissionKey( PERMISSION_RULE_CREATE );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_RULE_CREATE );
        rt.registerPermission( p );

        p = new Permission(  );
        p.setPermissionKey( PERMISSION_RULE_MODIFY );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_RULE_MODIFY );
        rt.registerPermission( p );

        p = new Permission(  );
        p.setPermissionKey( PERMISSION_RULE_DELETE );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_RULE_DELETE );
        rt.registerPermission( p );

        p = new Permission(  );
        p.setPermissionKey( PERMISSION_DATE_CALCULATED_MANAGE );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_DATE_CALCULATED_MANAGE );
        rt.registerPermission( p );

        p = new Permission(  );
        p.setPermissionKey( PERMISSION_DATE_CALCULATED_CREATE );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_DATE_CALCULATED_CREATE );
        rt.registerPermission( p );

        p = new Permission(  );
        p.setPermissionKey( PERMISSION_DATE_CALCULATED_MODIFY );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_DATE_CALCULATED_MODIFY );
        rt.registerPermission( p );

        p = new Permission(  );
        p.setPermissionKey( PERMISSION_DATE_CALCULATED_DELETE );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_DATE_CALCULATED_DELETE );
        rt.registerPermission( p );

        ResourceTypeManager.registerResourceType( rt );
    }

    /**
     * Returns a list of resource ids
     * @param locale The current locale
     * @return A list of resource ids
     */
    public ReferenceList getResourceIdList( Locale locale )
    {
        return null;
    }

    /**
     * Returns the Title of a given resource
     * @param strId The Id of the resource
     * @param locale The current locale
     * @return The Title of a given resource
     */
    public String getTitle( String strId, Locale locale )
    {
        return null;
    }
}
