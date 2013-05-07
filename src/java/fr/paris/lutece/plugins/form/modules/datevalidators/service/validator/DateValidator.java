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
package fr.paris.lutece.plugins.form.modules.datevalidators.service.validator;

import fr.paris.lutece.plugins.form.business.EntryHome;
import fr.paris.lutece.plugins.form.business.FormSubmit;
import fr.paris.lutece.plugins.form.business.IEntry;
import fr.paris.lutece.plugins.form.business.Response;
import fr.paris.lutece.plugins.form.modules.datevalidators.business.ComparatorHome;
import fr.paris.lutece.plugins.form.modules.datevalidators.business.OperatorHome;
import fr.paris.lutece.plugins.form.modules.datevalidators.business.Rule;
import fr.paris.lutece.plugins.form.modules.datevalidators.business.RuleHome;
import fr.paris.lutece.plugins.form.modules.datevalidators.business.UnitHome;
import fr.paris.lutece.plugins.form.modules.datevalidators.business.comparator.IComparator;
import fr.paris.lutece.plugins.form.modules.datevalidators.business.operator.IOperator;
import fr.paris.lutece.plugins.form.modules.datevalidators.business.unit.IUnit;
import fr.paris.lutece.plugins.form.modules.datevalidators.service.DateValidatorsPlugin;
import fr.paris.lutece.plugins.form.modules.datevalidators.service.DateValidatorsResourceIdService;
import fr.paris.lutece.plugins.form.modules.datevalidators.service.DateValidatorsService;
import fr.paris.lutece.plugins.form.modules.datevalidators.util.DateValidatorsConstants;
import fr.paris.lutece.plugins.form.service.validator.Validator;
import fr.paris.lutece.portal.business.rbac.RBAC;
import fr.paris.lutece.portal.service.admin.AdminUserService;
import fr.paris.lutece.portal.service.message.SiteMessage;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.message.SiteMessageService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.rbac.RBACService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.constants.Parameters;
import fr.paris.lutece.util.date.DateUtil;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.html.Paginator;
import fr.paris.lutece.util.sort.AttributeComparator;
import fr.paris.lutece.util.url.UrlItem;

import org.apache.commons.lang.StringUtils;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * DateValidator
 */
public class DateValidator extends Validator
{
    // Markers
    private static final String MARK_RULE_LIST = "rule_list";
    private static final String MARK_PERMISSION_MANAGE = "permission_manage";

    // Template
    private static final String TEMPLATE_MANAGE_RULE = "admin/plugins/form/modules/datevalidators/manage_rule.html";

    // Plugin
    private static Plugin _plugin;

    // Paginator
    private int _nDefaultItemsPerPage;
    private String _strCurrentPageIndex;
    private int _nItemsPerPage;

    /**
     * Returns the unique instance of the plugin
     * @return the unique instance of the plugin
     */
    private static Plugin getPlugin(  )
    {
        if ( _plugin == null )
        {
            _plugin = PluginService.getPlugin( DateValidatorsPlugin.PLUGIN_NAME );
        }

        return _plugin;
    }

    /**
    * Returns the validator interface
    * @param request {@link HttpServletRequest}
    * @param nIdForm the form id
    * @return the validator interface
    */
    public String getUI( HttpServletRequest request, int nIdForm )
    {
        _nDefaultItemsPerPage = AppPropertiesService.getPropertyInt( DateValidatorsConstants.PROPERTY_ITEMS_PER_PAGE, 10 );
        _strCurrentPageIndex = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndex );
        _nItemsPerPage = Paginator.getItemsPerPage( request, Paginator.PARAMETER_ITEMS_PER_PAGE, _nItemsPerPage,
                _nDefaultItemsPerPage );

        List<Rule> listRules = RuleHome.findRulesByForm( nIdForm, getPlugin(  ) );

        String strSortedAttributeName = request.getParameter( Parameters.SORTED_ATTRIBUTE_NAME );
        String strAscSort = null;

        if ( strSortedAttributeName != null )
        {
            strAscSort = request.getParameter( Parameters.SORTED_ASC );

            boolean bIsAscSort = Boolean.parseBoolean( strAscSort );

            Collections.sort( listRules, new AttributeComparator( strSortedAttributeName, bIsAscSort ) );
        }

        UrlItem urlItem = new UrlItem( AppPathService.getBaseUrl( request ) +
                DateValidatorsConstants.JSP_MANAGE_VALIDATOR );
        urlItem.addParameter( DateValidatorsConstants.PARAMETER_ID_FORM, nIdForm );

        if ( strSortedAttributeName != null )
        {
            urlItem.addParameter( Parameters.SORTED_ATTRIBUTE_NAME, strSortedAttributeName );
        }

        if ( strAscSort != null )
        {
            urlItem.addParameter( Parameters.SORTED_ASC, strAscSort );
        }

        Paginator<Rule> paginator = new Paginator<Rule>( listRules, _nItemsPerPage, urlItem.getUrl(  ),
                Paginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndex );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( DateValidatorsConstants.MARK_PAGINATOR, paginator );
        model.put( DateValidatorsConstants.MARK_NB_ITEMS_PER_PAGE, Integer.toString( _nItemsPerPage ) );
        model.put( MARK_RULE_LIST, paginator.getPageItems(  ) );
        model.put( DateValidatorsConstants.MARK_ID_FORM, nIdForm );
        model.put( DateValidatorsConstants.MARK_SORTED_ATTRIBUTE_NAME, strSortedAttributeName );
        model.put( DateValidatorsConstants.MARK_SORTED_ASC, strAscSort );
        model.put( DateValidatorsConstants.MARK_ENTRY_LIST, DateValidatorsService.getAuthorizedEntries( nIdForm ) );
        model.put( DateValidatorsConstants.MARK_COMPARATOR_LIST, ComparatorHome.findAll( getPlugin(  ) ) );
        addPermissions( request, model );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MANAGE_RULE, request.getLocale(  ), model );

        return template.getHtml(  );
    }

    /**
     * Add permissions in the model
     * @param request {@link HttpServletRequest}
     * @param model The model
     */
    private void addPermissions( HttpServletRequest request, Map<String, Object> model )
    {
        // Create
        boolean bPermissionCreate = false;

        if ( RBACService.isAuthorized( Rule.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    DateValidatorsResourceIdService.PERMISSION_RULE_CREATE, AdminUserService.getAdminUser( request ) ) )
        {
            bPermissionCreate = true;
        }

        model.put( DateValidatorsConstants.MARK_PERMISSION_CREATE, bPermissionCreate );

        // Modify
        boolean bPermissionModify = false;

        if ( RBACService.isAuthorized( Rule.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    DateValidatorsResourceIdService.PERMISSION_RULE_MODIFY, AdminUserService.getAdminUser( request ) ) )
        {
            bPermissionModify = true;
        }

        model.put( DateValidatorsConstants.MARK_PERMISSION_MODIFY, bPermissionModify );

        // Delete
        boolean bPermissionDelete = false;

        if ( RBACService.isAuthorized( Rule.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    DateValidatorsResourceIdService.PERMISSION_RULE_DELETE, AdminUserService.getAdminUser( request ) ) )
        {
            bPermissionDelete = true;
        }

        model.put( DateValidatorsConstants.MARK_PERMISSION_DELETE, bPermissionDelete );

        // Manage calculated dates
        boolean bPermissionManage = false;

        if ( RBACService.isAuthorized( Rule.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    DateValidatorsResourceIdService.PERMISSION_DATE_CALCULATED_MANAGE,
                    AdminUserService.getAdminUser( request ) ) )
        {
            bPermissionManage = true;
        }

        model.put( MARK_PERMISSION_MANAGE, bPermissionManage );
    }

    /**
    * Checks if the validator is associated with the form
    * @param nIdForm the form id
    * @return true if the validator is associated with the form, otherwise false
    */
    public boolean isAssociatedWithForm( int nIdForm )
    {
        return ( RuleHome.findRulesByForm( nIdForm, getPlugin(  ) ).size(  ) > 0 );
    }

    /**
     * Removes the associations with the form
     * @param nIdForm the form id
     */
    public void removeAssociationsWithForm( int nIdForm )
    {
        for ( Rule rule : RuleHome.findRulesByForm( nIdForm, getPlugin(  ) ) )
        {
            RuleHome.remove( rule.getIdRule(  ), getPlugin(  ) );
        }
    }

    /**
     * Validates the form
     * @param request {@link HttpServletRequest}
     * @param formSubmit the form submit
     * @param formPlugin the form plugin
     * @throws SiteMessageException throws SiteMessageException
     */
    public void validateForm( HttpServletRequest request, FormSubmit formSubmit, Plugin formPlugin )
        throws SiteMessageException
    {
        // Checks that all rules are followed
        for ( Rule rule : RuleHome.findRulesByForm( formSubmit.getForm(  ).getIdForm(  ), getPlugin(  ) ) )
        {
            // Entry
            if ( rule.getIdEntry2(  ) != null )
            {
                validateRuleEntry( request, formSubmit, formPlugin, rule );
            }

            // Reference date
            else if ( rule.getDateReference(  ) != null )
            {
                validateRuleDateReference( request, formSubmit, formPlugin, rule );
            }

            // Calculated date
            else if ( rule.getDateCalculated(  ) != null )
            {
                validateRuleDateCalculated( request, formSubmit, formPlugin, rule );
            }
        }
    }

    /**
     * Validates the rule of type entry
     * @param request {@link HttpServletRequest}
     * @param formSubmit the form submit
     * @param formPlugin the form plugin
     * @param rule the rule
     * @throws SiteMessageException throws SiteMessageException
     */
    private void validateRuleEntry( HttpServletRequest request, FormSubmit formSubmit, Plugin formPlugin, Rule rule )
        throws SiteMessageException
    {
        // Gets the dates
        Date dateEntry1 = null;
        Date dateEntry2 = null;

        for ( Response response : formSubmit.getListResponse(  ) )
        {
            if ( response.getEntry(  ).getIdEntry(  ) == rule.getIdEntry1(  ) )
            {
                dateEntry1 = new Date( Long.parseLong( new String( response.getValueResponse(  ) ) ) );
            }
            else if ( response.getEntry(  ).getIdEntry(  ) == rule.getIdEntry2(  ) )
            {
                dateEntry2 = new Date( Long.parseLong( new String( response.getValueResponse(  ) ) ) );
            }

            // Break
            if ( ( dateEntry1 != null ) && ( dateEntry2 != null ) )
            {
                break;
            }
        }

        // Gets the comparator
        String strClassName = ComparatorHome.findByPrimaryKey( rule.getIdComparator(  ), getPlugin(  ) ).getClassName(  );

        if ( ( dateEntry1 != null ) && ( dateEntry2 != null ) && StringUtils.isNotBlank( strClassName ) )
        {
            try
            {
                // Instanciates the comparator
                IComparator comparator = (IComparator) Class.forName( strClassName ).newInstance(  );

                // Compares the dates
                if ( !comparator.compare( dateEntry1, dateEntry2 ) )
                {
                    IEntry entry1 = EntryHome.findByPrimaryKey( rule.getIdEntry1(  ), formPlugin );
                    IEntry entry2 = EntryHome.findByPrimaryKey( rule.getIdEntry2(  ), formPlugin );

                    Object[] messageArgs = { entry1.getTitle(  ), entry2.getTitle(  ) };

                    SiteMessageService.setMessage( request, comparator.getMessage(  ), messageArgs,
                        SiteMessage.TYPE_STOP );
                }
            }
            catch ( InstantiationException e )
            {
                AppLogService.error( e.getMessage(  ), e );
            }
            catch ( IllegalAccessException e )
            {
                AppLogService.error( e.getMessage(  ), e );
            }
            catch ( ClassNotFoundException e )
            {
                AppLogService.error( e.getMessage(  ), e );
            }
        }
    }

    /**
     * Validates the rule of type reference date
     * @param request {@link HttpServletRequest}
     * @param formSubmit the form submit
     * @param formPlugin the form plugin
     * @param rule the rule
     * @throws SiteMessageException throws SiteMessageException
     */
    private void validateRuleDateReference( HttpServletRequest request, FormSubmit formSubmit, Plugin formPlugin,
        Rule rule ) throws SiteMessageException
    {
        // Gets the entry date
        Date dateEntry = null;

        for ( Response response : formSubmit.getListResponse(  ) )
        {
            if ( response.getEntry(  ).getIdEntry(  ) == rule.getIdEntry1(  ) )
            {
                dateEntry = new Date( Long.parseLong( new String( response.getValueResponse(  ) ) ) );

                break;
            }
        }

        // Gets the comparator
        String strClassName = ComparatorHome.findByPrimaryKey( rule.getIdComparator(  ), getPlugin(  ) ).getClassName(  );

        if ( ( dateEntry != null ) && StringUtils.isNotBlank( strClassName ) )
        {
            try
            {
                // Instanciates the comparator
                IComparator comparator = (IComparator) Class.forName( strClassName ).newInstance(  );

                // Compares the dates
                if ( !comparator.compare( dateEntry, rule.getDateReference(  ) ) )
                {
                    IEntry entry = EntryHome.findByPrimaryKey( rule.getIdEntry1(  ), formPlugin );
                    String strDateReference = DateUtil.getDateString( rule.getDateReference(  ), request.getLocale(  ) );

                    Object[] messageArgs = { entry.getTitle(  ), strDateReference };

                    SiteMessageService.setMessage( request, comparator.getMessage(  ), messageArgs,
                        SiteMessage.TYPE_STOP );
                }
            }
            catch ( InstantiationException e )
            {
                AppLogService.error( e.getMessage(  ), e );
            }
            catch ( IllegalAccessException e )
            {
                AppLogService.error( e.getMessage(  ), e );
            }
            catch ( ClassNotFoundException e )
            {
                AppLogService.error( e.getMessage(  ), e );
            }
        }
    }

    /**
     * Validates the rule of type calculated date
     * @param request {@link HttpServletRequest}
     * @param formSubmit the form submit
     * @param formPlugin the form plugin
     * @param rule the rule
     * @throws SiteMessageException throws SiteMessageException
     */
    private void validateRuleDateCalculated( HttpServletRequest request, FormSubmit formSubmit, Plugin formPlugin,
        Rule rule ) throws SiteMessageException
    {
        // Gets the entry date
        Date dateEntry = null;

        for ( Response response : formSubmit.getListResponse(  ) )
        {
            if ( response.getEntry(  ).getIdEntry(  ) == rule.getIdEntry1(  ) )
            {
                dateEntry = new Date( Long.parseLong( new String( response.getValueResponse(  ) ) ) );

                break;
            }
        }

        // Gets the calendar field (unit)
        String strUnitClassName = UnitHome.findByPrimaryKey( rule.getDateCalculated(  ).getUnit(  ).getIdUnit(  ),
                getPlugin(  ) ).getClassName(  );
        Integer nCalendarField = null;

        if ( StringUtils.isNotBlank( strUnitClassName ) )
        {
            try
            {
                // Instanciates the unit
                IUnit unit = (IUnit) Class.forName( strUnitClassName ).newInstance(  );

                // Gets the calendarField
                nCalendarField = unit.getCalendarField(  );
            }
            catch ( InstantiationException e )
            {
                AppLogService.error( e.getMessage(  ), e );
            }
            catch ( IllegalAccessException e )
            {
                AppLogService.error( e.getMessage(  ), e );
            }
            catch ( ClassNotFoundException e )
            {
                AppLogService.error( e.getMessage(  ), e );
            }
        }

        // Gets the calculated date (operator)
        String strOperatorClassName = OperatorHome.findByPrimaryKey( rule.getDateCalculated(  ).getOperator(  )
                                                                         .getIdOperator(  ), getPlugin(  ) )
                                                  .getClassName(  );
        Date dateCalculated = null;

        if ( ( nCalendarField != null ) && StringUtils.isNotBlank( strOperatorClassName ) )
        {
            try
            {
                // Instanciates the operator
                IOperator operator = (IOperator) Class.forName( strOperatorClassName ).newInstance(  );

                // Calculates the date
                dateCalculated = operator.calculate( rule.getDateCalculated(  ).getDateReference(  ), nCalendarField,
                        rule.getDateCalculated(  ).getNumber(  ) );
            }
            catch ( InstantiationException e )
            {
                AppLogService.error( e.getMessage(  ), e );
            }
            catch ( IllegalAccessException e )
            {
                AppLogService.error( e.getMessage(  ), e );
            }
            catch ( ClassNotFoundException e )
            {
                AppLogService.error( e.getMessage(  ), e );
            }
        }

        // Gets the comparator
        String strComparatorClassName = ComparatorHome.findByPrimaryKey( rule.getIdComparator(  ), getPlugin(  ) )
                                                      .getClassName(  );

        if ( ( dateEntry != null ) && ( dateCalculated != null ) && StringUtils.isNotBlank( strComparatorClassName ) )
        {
            try
            {
                // Instanciates the comparator
                IComparator comparator = (IComparator) Class.forName( strComparatorClassName ).newInstance(  );

                // Compares the dates
                if ( !comparator.compare( dateEntry, dateCalculated ) )
                {
                    IEntry entry = EntryHome.findByPrimaryKey( rule.getIdEntry1(  ), formPlugin );
                    String strDateCalculated = DateUtil.getDateString( dateCalculated, request.getLocale(  ) );

                    Object[] messageArgs = { entry.getTitle(  ), strDateCalculated };

                    SiteMessageService.setMessage( request, comparator.getMessage(  ), messageArgs,
                        SiteMessage.TYPE_STOP );
                }
            }
            catch ( InstantiationException e )
            {
                AppLogService.error( e.getMessage(  ), e );
            }
            catch ( IllegalAccessException e )
            {
                AppLogService.error( e.getMessage(  ), e );
            }
            catch ( ClassNotFoundException e )
            {
                AppLogService.error( e.getMessage(  ), e );
            }
        }
    }
}
