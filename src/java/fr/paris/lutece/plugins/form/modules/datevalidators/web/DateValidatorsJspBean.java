/*
 * Copyright (c) 2002-2017, Mairie de Paris
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
package fr.paris.lutece.plugins.form.modules.datevalidators.web;

import fr.paris.lutece.plugins.form.modules.datevalidators.business.ComparatorHome;
import fr.paris.lutece.plugins.form.modules.datevalidators.business.DateCalculated;
import fr.paris.lutece.plugins.form.modules.datevalidators.business.DateCalculatedHome;
import fr.paris.lutece.plugins.form.modules.datevalidators.business.Operator;
import fr.paris.lutece.plugins.form.modules.datevalidators.business.OperatorHome;
import fr.paris.lutece.plugins.form.modules.datevalidators.business.Rule;
import fr.paris.lutece.plugins.form.modules.datevalidators.business.RuleHome;
import fr.paris.lutece.plugins.form.modules.datevalidators.business.Unit;
import fr.paris.lutece.plugins.form.modules.datevalidators.business.UnitHome;
import fr.paris.lutece.plugins.form.modules.datevalidators.service.DateValidatorsPlugin;
import fr.paris.lutece.plugins.form.modules.datevalidators.service.DateValidatorsResourceIdService;
import fr.paris.lutece.plugins.form.modules.datevalidators.service.DateValidatorsService;
import fr.paris.lutece.plugins.form.modules.datevalidators.util.DateValidatorsConstants;
import fr.paris.lutece.plugins.form.modules.datevalidators.util.DateValidatorsUtils;
import fr.paris.lutece.plugins.form.web.FormJspBean;
import fr.paris.lutece.portal.business.rbac.RBAC;
import fr.paris.lutece.portal.service.admin.AdminUserService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.rbac.RBACService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.constants.Parameters;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.date.DateUtil;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.html.Paginator;
import fr.paris.lutece.util.sort.AttributeComparator;
import fr.paris.lutece.util.url.UrlItem;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * DateValidatorsJspBean
 */
public class DateValidatorsJspBean extends FormJspBean
{
    // Markers
    private static final String MARK_TYPE_ENTRY = "type_entry";
    private static final String MARK_TYPE_DATE_REFERENCE = "type_date_reference";
    private static final String MARK_TYPE_DATE_CALCULATED = "type_date_calculated";
    private static final String MARK_TYPE = "type";
    private static final String MARK_LOCALE = "locale";
    private static final String MARK_RULE = "rule";
    private static final String MARK_DATE_CALCULATED_LIST = "date_calculated_list";
    private static final String MARK_OPERATOR_LIST = "operator_list";
    private static final String MARK_UNIT_LIST = "unit_list";
    private static final String MARK_DATE_CALCULATED = "date_calculated";

    // Parameters
    private static final String PARAMETER_ID_RULE = "id_rule";
    private static final String PARAMETER_ID_ENTRY1 = "id_entry1";
    private static final String PARAMETER_ID_ENTRY2 = "id_entry2";
    private static final String PARAMETER_ID_COMPARATOR = "id_comparator";
    private static final String PARAMETER_TYPE = "type";
    private static final String PARAMETER_CANCEL = "cancel";
    private static final String PARAMETER_BACK = "back";
    private static final String PARAMETER_DATE_REFERENCE = "date_reference";
    private static final String PARAMETER_ID_DATE_CALCULATED = "id_date_calculated";
    private static final String PARAMETER_ID_OPERATOR = "id_operator";
    private static final String PARAMETER_NUMBER = "number";
    private static final String PARAMETER_ID_UNIT = "id_unit";

    // Titles
    private static final String PROPERTY_SELECT_TYPE_TITLE = "module.form.datevalidators.selectType.title";
    private static final String PROPERTY_CREATE_RULE_TITLE = "module.form.datevalidators.createRule.title";
    private static final String PROPERTY_MODIFY_RULE_TITLE = "module.form.datevalidators.modifyRule.title";
    private static final String PROPERTY_MANAGE_DATE_CALCULATED_TITLE = "module.form.datevalidators.manageDateCalculated.title";
    private static final String PROPERTY_CREATE_DATE_CALCULATED_TITLE = "module.form.datevalidators.createDateCalculated.title";
    private static final String PROPERTY_MODIFY_DATE_CALCULATED_TITLE = "module.form.datevalidators.modifyDateCalculated.title";

    // Messages
    private static final String MESSAGE_MANDATORY_FIELD = "module.form.datevalidators.message.mandatory.field";
    private static final String MESSAGE_ERROR_IDENTICAL_ENTRIES = "module.form.datevalidators.message.error.identicalEntries";
    private static final String MESSAGE_ERROR_RULE_ALREADY_EXISTS_ENTRY = "module.form.datevalidators.message.error.ruleAlreadyExists.entry";
    private static final String MESSAGE_ERROR_RULE_ALREADY_EXISTS_DATE_REFERENCE = "module.form.datevalidators.message.error.ruleAlreadyExists.dateReference";
    private static final String MESSAGE_ERROR_RULE_ALREADY_EXISTS_DATE_CALCULATED = "module.form.datevalidators.message.error.ruleAlreadyExists.dateCalculated";
    private static final String MESSAGE_ERROR_DATE_CALCULATED_ALREADY_EXISTS = "module.form.datevalidators.message.error.dateCalculatedAlreadyExists";
    private static final String MESSAGE_CONFIRM_REMOVE_RULE = "module.form.datevalidators.message.confirmRemoveRule";
    private static final String MESSAGE_NUMERIC_FIELD = "module.form.datevalidators.message.numeric.field";
    private static final String MESSAGE_ERROR_TYPE_NOT_EXISTS = "module.form.datevalidators.message.error.typeNotExists";
    private static final String MESSAGE_CONFIRM_REMOVE_DATE_CALCULATED = "module.form.datevalidators.message.confirmRemoveDateCalculated";

    // Fields
    private static final String FIELD_SELECT_TYPE_TYPE = "module.form.datevalidators.selectType.labelType";
    private static final String FIELD_RULE_ENTRY_ENTRY = "module.form.datevalidators.createRule.entry.labelEntry1";
    private static final String FIELD_RULE_DATE_REFERENCE_ENTRY = "module.form.datevalidators.createRule.dateReference.labelEntry";
    private static final String FIELD_RULE_DATE_CALCULATED_ENTRY = "module.form.datevalidators.createRule.dateCalculated.labelEntry";
    private static final String FIELD_RULE_ENTRY2 = "module.form.datevalidators.createRule.entry.labelEntry2";
    private static final String FIELD_RULE_COMPARATOR = "module.form.datevalidators.createRule.labelComparator";
    private static final String FIELD_RULE_DATE_REFERENCE = "module.form.datevalidators.createRule.dateReference.labelDateReference";
    private static final String FIELD_RULE_DATE_CALCULATED = "module.form.datevalidators.createRule.dateCalculated.labelDateCalculated";
    private static final String FIELD_DATE_CALCULATED_DATE_REFERENCE = "module.form.datevalidators.createDateCalculated.labelDateReference";
    private static final String FIELD_DATE_CALCULATED_OPERATOR = "module.form.datevalidators.createDateCalculated.labelOperator";
    private static final String FIELD_DATE_CALCULATED_NUMBER = "module.form.datevalidators.createDateCalculated.labelNumber";
    private static final String FIELD_DATE_CALCULATED_UNIT = "module.form.datevalidators.createDateCalculated.labelUnit";

    // Templates
    private static final String TEMPLATE_SELECT_TYPE = "admin/plugins/form/modules/datevalidators/select_type.html";
    private static final String TEMPLATE_CREATE_RULE_ENTRY = "admin/plugins/form/modules/datevalidators/create_rule_entry.html";
    private static final String TEMPLATE_CREATE_RULE_DATE_REFERENCE = "admin/plugins/form/modules/datevalidators/create_rule_date_reference.html";
    private static final String TEMPLATE_CREATE_RULE_DATE_CALCULATED = "admin/plugins/form/modules/datevalidators/create_rule_date_calculated.html";
    private static final String TEMPLATE_MODIFY_RULE_ENTRY = "admin/plugins/form/modules/datevalidators/modify_rule_entry.html";
    private static final String TEMPLATE_MODIFY_RULE_DATE_REFERENCE = "admin/plugins/form/modules/datevalidators/modify_rule_date_reference.html";
    private static final String TEMPLATE_MODIFY_RULE_DATE_CALCULATED = "admin/plugins/form/modules/datevalidators/modify_rule_date_calculated.html";
    private static final String TEMPLATE_MANAGE_DATE_CALCULATED = "admin/plugins/form/modules/datevalidators/manage_date_calculated.html";
    private static final String TEMPLATE_CREATE_DATE_CALCULATED = "admin/plugins/form/modules/datevalidators/create_date_calculated.html";
    private static final String TEMPLATE_MODIFY_DATE_CALCULATED = "admin/plugins/form/modules/datevalidators/modify_date_calculated.html";

    // JSPs
    private static final String JSP_SELECT_TYPE = "jsp/admin/plugins/form/modules/datevalidators/SelectType.jsp";
    private static final String JSP_CREATE_RULE_ENTRY = "/jsp/admin/plugins/form/modules/datevalidators/CreateRuleEntry.jsp";
    private static final String JSP_CREATE_RULE_DATE_REFERENCE = "jsp/admin/plugins/form/modules/datevalidators/CreateRuleDateReference.jsp";
    private static final String JSP_CREATE_RULE_DATE_CALCULATED = "jsp/admin/plugins/form/modules/datevalidators/CreateRuleDateCalculated.jsp";
    private static final String JSP_DO_REMOVE_RULE = "jsp/admin/plugins/form/modules/datevalidators/DoRemoveRule.jsp";
    private static final String JSP_MANAGE_DATE_CALCULATED = "jsp/admin/plugins/form/modules/datevalidators/ManageDateCalculated.jsp";
    private static final String JSP_DO_REMOVE_DATE_CALCULATED = "jsp/admin/plugins/form/modules/datevalidators/DoRemoveDateCalculated.jsp";

    // Plugin
    private static final Plugin PLUGIN = PluginService.getPlugin( DateValidatorsPlugin.PLUGIN_NAME );

    // Paginator
    private int _nDefaultItemsPerPage;
    private String _strCurrentPageIndex;
    private int _nItemsPerPage;

    /**
     * Gets the type selection page
     *
     * @param request The HTTP request
     * @return The type selection page
     */
    public String getSelectType( HttpServletRequest request )
    {
        if ( !RBACService.isAuthorized( Rule.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    DateValidatorsResourceIdService.PERMISSION_RULE_CREATE, getUser(  ) ) )
        {
            return getManageValidator( request );
        }

        Integer nIdForm = DateValidatorsUtils.stringToInt( request.getParameter( 
                    DateValidatorsConstants.PARAMETER_ID_FORM ) );

        if ( nIdForm == null )
        {
            return getManageValidator( request );
        }

        setPageTitleProperty( PROPERTY_SELECT_TYPE_TITLE );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( DateValidatorsConstants.MARK_ID_FORM, nIdForm );
        model.put( MARK_TYPE_ENTRY, DateValidatorsConstants.TYPE_ENTRY );
        model.put( MARK_TYPE_DATE_REFERENCE, DateValidatorsConstants.TYPE_DATE_REFERENCE );
        model.put( MARK_TYPE_DATE_CALCULATED, DateValidatorsConstants.TYPE_DATE_CALCULATED );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_SELECT_TYPE, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Performs the type selection
     *
     * @param request The HTTP request
     * @return The URL to go after performing the action
     */
    public String doSelectType( HttpServletRequest request )
    {
        Integer nIdForm = DateValidatorsUtils.stringToInt( request.getParameter( 
                    DateValidatorsConstants.PARAMETER_ID_FORM ) );

        if ( !RBACService.isAuthorized( Rule.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    DateValidatorsResourceIdService.PERMISSION_RULE_CREATE, getUser(  ) ) )
        {
            return getJspManageValidator( request, nIdForm );
        }

        // Cancel
        if ( StringUtils.isNotBlank( request.getParameter( PARAMETER_CANCEL ) ) )
        {
            return getJspManageValidator( request, nIdForm );
        }

        Integer nType = DateValidatorsUtils.stringToInt( request.getParameter( PARAMETER_TYPE ) );

        if ( nType == null )
        {
            Object[] tabRequiredFields = { I18nService.getLocalizedString( FIELD_SELECT_TYPE_TYPE, getLocale(  ) ) };

            return AdminMessageService.getMessageUrl( request, MESSAGE_MANDATORY_FIELD, tabRequiredFields,
                AdminMessage.TYPE_STOP );
        }

        String strJsp;

        switch ( nType )
        {
            case DateValidatorsConstants.TYPE_ENTRY:
                strJsp = JSP_CREATE_RULE_ENTRY;

                break;

            case DateValidatorsConstants.TYPE_DATE_REFERENCE:
                strJsp = JSP_CREATE_RULE_DATE_REFERENCE;

                break;

            case DateValidatorsConstants.TYPE_DATE_CALCULATED:
                strJsp = JSP_CREATE_RULE_DATE_CALCULATED;

                break;

            default:
                strJsp = DateValidatorsConstants.JSP_MANAGE_VALIDATOR;

                break;
        }

        UrlItem url = new UrlItem( AppPathService.getBaseUrl( request ) + strJsp );
        url.addParameter( DateValidatorsConstants.PARAMETER_ID_FORM, nIdForm );

        return url.getUrl(  );
    }

    /**
     * Gets the rule creation page for the type entry
     *
     * @param request The HTTP request
     * @return The rule creation page for the type entry
     */
    public String getCreateRuleEntry( HttpServletRequest request )
    {
        if ( !RBACService.isAuthorized( Rule.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    DateValidatorsResourceIdService.PERMISSION_RULE_CREATE, getUser(  ) ) )
        {
            return getManageValidator( request );
        }

        Integer nIdForm = DateValidatorsUtils.stringToInt( request.getParameter( 
                    DateValidatorsConstants.PARAMETER_ID_FORM ) );

        if ( nIdForm == null )
        {
            return getManageValidator( request );
        }

        setPageTitleProperty( PROPERTY_CREATE_RULE_TITLE );

        ReferenceList refListComparator = ComparatorHome.findAll( PLUGIN );
        ReferenceItem refItem = new ReferenceItem(  );
        refItem.setCode( String.valueOf( -1 ) );
        refItem.setName( DateValidatorsConstants.EMPTY_STRING );
        refListComparator.add( 0, refItem );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( DateValidatorsConstants.MARK_ID_FORM, nIdForm );
        model.put( DateValidatorsConstants.MARK_ENTRY_LIST, DateValidatorsService.getAuthorizedEntries( nIdForm ) );
        model.put( DateValidatorsConstants.MARK_COMPARATOR_LIST, refListComparator );
        model.put( MARK_TYPE, DateValidatorsConstants.TYPE_ENTRY );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_RULE_ENTRY, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Gets the rule creation page for the type reference date
     *
     * @param request The HTTP request
     * @return The rule creation page for the type reference date
     */
    public String getCreateRuleDateReference( HttpServletRequest request )
    {
        if ( !RBACService.isAuthorized( Rule.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    DateValidatorsResourceIdService.PERMISSION_RULE_CREATE, getUser(  ) ) )
        {
            return getManageValidator( request );
        }

        Integer nIdForm = DateValidatorsUtils.stringToInt( request.getParameter( 
                    DateValidatorsConstants.PARAMETER_ID_FORM ) );

        if ( nIdForm == null )
        {
            return getManageValidator( request );
        }

        setPageTitleProperty( PROPERTY_CREATE_RULE_TITLE );

        ReferenceList refListComparator = ComparatorHome.findAll( PLUGIN );
        ReferenceItem refItem = new ReferenceItem(  );
        refItem.setCode( String.valueOf( -1 ) );
        refItem.setName( DateValidatorsConstants.EMPTY_STRING );
        refListComparator.add( 0, refItem );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( DateValidatorsConstants.MARK_ID_FORM, nIdForm );
        model.put( DateValidatorsConstants.MARK_ENTRY_LIST, DateValidatorsService.getAuthorizedEntries( nIdForm ) );
        model.put( DateValidatorsConstants.MARK_COMPARATOR_LIST, refListComparator );
        model.put( MARK_TYPE, DateValidatorsConstants.TYPE_DATE_REFERENCE );
        model.put( MARK_LOCALE, getLocale(  ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_RULE_DATE_REFERENCE, getLocale(  ),
                model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Gets the rule creation page for the type calculated date
     *
     * @param request The HTTP request
     * @return The rule creation page for the type calculated date
     */
    public String getCreateRuleDateCalculated( HttpServletRequest request )
    {
        if ( !RBACService.isAuthorized( Rule.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    DateValidatorsResourceIdService.PERMISSION_RULE_CREATE, getUser(  ) ) )
        {
            return getManageValidator( request );
        }

        Integer nIdForm = DateValidatorsUtils.stringToInt( request.getParameter( 
                    DateValidatorsConstants.PARAMETER_ID_FORM ) );

        if ( nIdForm == null )
        {
            return getManageValidator( request );
        }

        setPageTitleProperty( PROPERTY_CREATE_RULE_TITLE );

        ReferenceList refListComparator = ComparatorHome.findAll( PLUGIN );
        ReferenceItem refItem = new ReferenceItem(  );
        refItem.setCode( String.valueOf( -1 ) );
        refItem.setName( DateValidatorsConstants.EMPTY_STRING );
        refListComparator.add( 0, refItem );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( DateValidatorsConstants.MARK_ID_FORM, nIdForm );
        model.put( DateValidatorsConstants.MARK_ENTRY_LIST, DateValidatorsService.getAuthorizedEntries( nIdForm ) );
        model.put( DateValidatorsConstants.MARK_COMPARATOR_LIST, refListComparator );
        model.put( MARK_DATE_CALCULATED_LIST, DateCalculatedHome.findAll( PLUGIN ) );
        model.put( MARK_TYPE, DateValidatorsConstants.TYPE_DATE_CALCULATED );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_RULE_DATE_CALCULATED, getLocale(  ),
                model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Performs the rule creation
     *
     * @param request The HTTP request
     * @return The URL to go after performing the action
     */
    public String doCreateRule( HttpServletRequest request )
    {
        Integer nIdForm = DateValidatorsUtils.stringToInt( request.getParameter( 
                    DateValidatorsConstants.PARAMETER_ID_FORM ) );

        if ( !RBACService.isAuthorized( Rule.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    DateValidatorsResourceIdService.PERMISSION_RULE_CREATE, getUser(  ) ) )
        {
            return getJspManageValidator( request, nIdForm );
        }

        // Back
        if ( StringUtils.isNotBlank( request.getParameter( PARAMETER_BACK ) ) )
        {
            UrlItem url = new UrlItem( AppPathService.getBaseUrl( request ) + JSP_SELECT_TYPE );
            url.addParameter( DateValidatorsConstants.PARAMETER_ID_FORM, nIdForm );

            return url.getUrl(  );
        }

        Integer nType = DateValidatorsUtils.stringToInt( request.getParameter( PARAMETER_TYPE ) );

        if ( nType == null )
        {
            return getJspManageValidator( request, nIdForm );
        }

        Rule rule = new Rule(  );
        rule.setIdForm( nIdForm );

        // Get fields
        String strError = getRuleFields( request, rule, nType );

        if ( strError != null )
        {
            return strError;
        }

        RuleHome.create( rule, PLUGIN );

        return getJspManageValidator( request, nIdForm );
    }

    /**
     * Gets the rule modification page for the type entry
     *
     * @param request The HTTP request
     * @return The rule modification page for the type entry
     */
    public String getModifyRuleEntry( HttpServletRequest request )
    {
        if ( !RBACService.isAuthorized( Rule.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    DateValidatorsResourceIdService.PERMISSION_RULE_MODIFY, getUser(  ) ) )
        {
            return getManageValidator( request );
        }

        Integer nIdRule = DateValidatorsUtils.stringToInt( request.getParameter( PARAMETER_ID_RULE ) );

        if ( nIdRule == null )
        {
            return getManageValidator( request );
        }

        Rule rule = RuleHome.findByPrimaryKey( nIdRule, PLUGIN );

        if ( rule == null )
        {
            return getManageValidator( request );
        }

        setPageTitleProperty( PROPERTY_MODIFY_RULE_TITLE );

        ReferenceList refListComparator = ComparatorHome.findAll( PLUGIN );
        ReferenceItem refItem = new ReferenceItem(  );
        refItem.setCode( String.valueOf( -1 ) );
        refItem.setName( DateValidatorsConstants.EMPTY_STRING );
        refListComparator.add( 0, refItem );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_RULE, rule );
        model.put( DateValidatorsConstants.MARK_ENTRY_LIST,
            DateValidatorsService.getAuthorizedEntries( rule.getIdForm(  ) ) );
        model.put( DateValidatorsConstants.MARK_COMPARATOR_LIST, refListComparator );
        model.put( MARK_TYPE, DateValidatorsConstants.TYPE_ENTRY );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_RULE_ENTRY, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Gets the rule modification page for the type reference date
     *
     * @param request The HTTP request
     * @return The rule modification page for the type reference date
     */
    public String getModifyRuleDateReference( HttpServletRequest request )
    {
        if ( !RBACService.isAuthorized( Rule.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    DateValidatorsResourceIdService.PERMISSION_RULE_MODIFY, getUser(  ) ) )
        {
            return getManageValidator( request );
        }

        Integer nIdRule = DateValidatorsUtils.stringToInt( request.getParameter( PARAMETER_ID_RULE ) );

        if ( nIdRule == null )
        {
            return getManageValidator( request );
        }

        Rule rule = RuleHome.findByPrimaryKey( nIdRule, PLUGIN );

        if ( rule == null )
        {
            return getManageValidator( request );
        }

        setPageTitleProperty( PROPERTY_MODIFY_RULE_TITLE );

        ReferenceList refListComparator = ComparatorHome.findAll( PLUGIN );
        ReferenceItem refItem = new ReferenceItem(  );
        refItem.setCode( String.valueOf( -1 ) );
        refItem.setName( DateValidatorsConstants.EMPTY_STRING );
        refListComparator.add( 0, refItem );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_RULE, rule );
        model.put( DateValidatorsConstants.MARK_ENTRY_LIST,
            DateValidatorsService.getAuthorizedEntries( rule.getIdForm(  ) ) );
        model.put( DateValidatorsConstants.MARK_COMPARATOR_LIST, refListComparator );
        model.put( MARK_TYPE, DateValidatorsConstants.TYPE_DATE_REFERENCE );
        model.put( MARK_LOCALE, request.getLocale(  ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_RULE_DATE_REFERENCE, getLocale(  ),
                model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Gets the rule modification page for the type calculated date
     *
     * @param request The HTTP request
     * @return The rule modification page for the type calculated date
     */
    public String getModifyRuleDateCalculated( HttpServletRequest request )
    {
        if ( !RBACService.isAuthorized( Rule.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    DateValidatorsResourceIdService.PERMISSION_RULE_MODIFY, getUser(  ) ) )
        {
            return getManageValidator( request );
        }

        Integer nIdRule = DateValidatorsUtils.stringToInt( request.getParameter( PARAMETER_ID_RULE ) );

        if ( nIdRule == null )
        {
            return getManageValidator( request );
        }

        Rule rule = RuleHome.findByPrimaryKey( nIdRule, PLUGIN );

        if ( rule == null )
        {
            return getManageValidator( request );
        }

        setPageTitleProperty( PROPERTY_MODIFY_RULE_TITLE );

        ReferenceList refListComparator = ComparatorHome.findAll( PLUGIN );
        ReferenceItem refItem = new ReferenceItem(  );
        refItem.setCode( String.valueOf( -1 ) );
        refItem.setName( DateValidatorsConstants.EMPTY_STRING );
        refListComparator.add( 0, refItem );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_RULE, rule );
        model.put( DateValidatorsConstants.MARK_ENTRY_LIST,
            DateValidatorsService.getAuthorizedEntries( rule.getIdForm(  ) ) );
        model.put( DateValidatorsConstants.MARK_COMPARATOR_LIST, refListComparator );
        model.put( MARK_DATE_CALCULATED_LIST, DateCalculatedHome.findAll( PLUGIN ) );
        model.put( MARK_TYPE, DateValidatorsConstants.TYPE_DATE_CALCULATED );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_RULE_DATE_CALCULATED, getLocale(  ),
                model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Performs the rule modification
     *
     * @param request The HTTP request
     * @return The URL to go after performing the action
     */
    public String doModifyRule( HttpServletRequest request )
    {
        Integer nIdForm = DateValidatorsUtils.stringToInt( request.getParameter( 
                    DateValidatorsConstants.PARAMETER_ID_FORM ) );

        if ( !RBACService.isAuthorized( Rule.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    DateValidatorsResourceIdService.PERMISSION_RULE_MODIFY, getUser(  ) ) )
        {
            return getJspManageValidator( request, nIdForm );
        }

        // Cancel
        if ( StringUtils.isNotBlank( request.getParameter( PARAMETER_CANCEL ) ) )
        {
            return getJspManageValidator( request, nIdForm );
        }

        Integer nIdRule = DateValidatorsUtils.stringToInt( request.getParameter( PARAMETER_ID_RULE ) );

        if ( nIdRule == null )
        {
            return getJspManageValidator( request, nIdForm );
        }

        Rule rule = RuleHome.findByPrimaryKey( nIdRule, PLUGIN );

        if ( rule == null )
        {
            return getJspManageValidator( request, nIdForm );
        }

        Integer nType = DateValidatorsUtils.stringToInt( request.getParameter( PARAMETER_TYPE ) );

        if ( nType == null )
        {
            return getJspManageValidator( request, nIdForm );
        }

        // Get fields
        String strError = getRuleFields( request, rule, nType );

        if ( strError != null )
        {
            return strError;
        }

        RuleHome.update( rule, PLUGIN );

        return getJspManageValidator( request, nIdForm );
    }

    /**
     * Gets the rule form fields
     *
     * @param request The HTTP request
     * @param rule The rule
     * @param nType The rule type
     * @return Null if no error, else the AdminMessage URL
     */
    private String getRuleFields( HttpServletRequest request, Rule rule, int nType )
    {
        Integer nIdEntry1 = DateValidatorsUtils.stringToInt( request.getParameter( PARAMETER_ID_ENTRY1 ) );
        Integer nIdComparator = DateValidatorsUtils.stringToInt( request.getParameter( PARAMETER_ID_COMPARATOR ) );

        String strFieldError = DateValidatorsConstants.EMPTY_STRING;

        if ( nIdEntry1 == null )
        {
            switch ( nType )
            {
                case DateValidatorsConstants.TYPE_ENTRY:
                    strFieldError = FIELD_RULE_ENTRY_ENTRY;

                    break;

                case DateValidatorsConstants.TYPE_DATE_REFERENCE:
                    strFieldError = FIELD_RULE_DATE_REFERENCE_ENTRY;

                    break;

                case DateValidatorsConstants.TYPE_DATE_CALCULATED:
                    strFieldError = FIELD_RULE_DATE_CALCULATED_ENTRY;

                    break;

                default:
                    return AdminMessageService.getMessageUrl( request, MESSAGE_ERROR_TYPE_NOT_EXISTS,
                        AdminMessage.TYPE_STOP );
            }
        }
        else if ( nIdComparator == null )
        {
            strFieldError = FIELD_RULE_COMPARATOR;
        }

        if ( StringUtils.isNotBlank( strFieldError ) )
        {
            Object[] tabRequiredFields = { I18nService.getLocalizedString( strFieldError, getLocale(  ) ) };

            return AdminMessageService.getMessageUrl( request, MESSAGE_MANDATORY_FIELD, tabRequiredFields,
                AdminMessage.TYPE_STOP );
        }

        rule.setIdEntry1( nIdEntry1 );
        rule.setIdComparator( nIdComparator );

        switch ( nType )
        {
            case DateValidatorsConstants.TYPE_ENTRY:

                Integer nIdEntry2 = DateValidatorsUtils.stringToInt( request.getParameter( PARAMETER_ID_ENTRY2 ) );

                if ( nIdEntry2 == null )
                {
                    Object[] tabRequiredFields = { I18nService.getLocalizedString( FIELD_RULE_ENTRY2, getLocale(  ) ) };

                    return AdminMessageService.getMessageUrl( request, MESSAGE_MANDATORY_FIELD, tabRequiredFields,
                        AdminMessage.TYPE_STOP );
                }

                // Entries may not be identical
                if ( nIdEntry1 == nIdEntry2 )
                {
                    return AdminMessageService.getMessageUrl( request, MESSAGE_ERROR_IDENTICAL_ENTRIES,
                        AdminMessage.TYPE_STOP );
                }

                // Checks if a rule already exists for these entries
                for ( Rule currentRule : RuleHome.findRulesByForm( rule.getIdForm(  ), PLUGIN ) )
                {
                    if ( ( currentRule.getIdRule(  ) != rule.getIdRule(  ) ) &&
                            ( ( ( currentRule.getIdEntry1(  ) == nIdEntry1 ) && ( currentRule.getIdEntry2(  ) != null ) &&
                            ( currentRule.getIdEntry2(  ) == nIdEntry2 ) ) ||
                            ( ( currentRule.getIdEntry1(  ) == nIdEntry2 ) && ( currentRule.getIdEntry2(  ) != null ) &&
                            ( currentRule.getIdEntry2(  ) == nIdEntry1 ) ) ) )
                    {
                        return AdminMessageService.getMessageUrl( request, MESSAGE_ERROR_RULE_ALREADY_EXISTS_ENTRY,
                            AdminMessage.TYPE_STOP );
                    }
                }

                rule.setIdEntry2( nIdEntry2 );

                break;

            case DateValidatorsConstants.TYPE_DATE_REFERENCE:

                Timestamp dateReference = DateUtil.formatTimestamp( request.getParameter( PARAMETER_DATE_REFERENCE ),
                        request.getLocale(  ) );

                if ( dateReference == null )
                {
                    Object[] tabRequiredFields = 
                        {
                            I18nService.getLocalizedString( FIELD_RULE_DATE_REFERENCE, getLocale(  ) )
                        };

                    return AdminMessageService.getMessageUrl( request, MESSAGE_MANDATORY_FIELD, tabRequiredFields,
                        AdminMessage.TYPE_STOP );
                }

                // Checks if a rule already exists for this entry and this reference date
                for ( Rule currentRule : RuleHome.findRulesByForm( rule.getIdForm(  ), PLUGIN ) )
                {
                    if ( ( currentRule.getIdRule(  ) != rule.getIdRule(  ) ) &&
                            ( currentRule.getIdEntry1(  ) == nIdEntry1 ) && ( currentRule.getDateReference(  ) != null ) &&
                            currentRule.getDateReference(  ).equals( dateReference ) )
                    {
                        return AdminMessageService.getMessageUrl( request,
                            MESSAGE_ERROR_RULE_ALREADY_EXISTS_DATE_REFERENCE, AdminMessage.TYPE_STOP );
                    }
                }

                rule.setDateReference( dateReference );

                break;

            case DateValidatorsConstants.TYPE_DATE_CALCULATED:

                Integer nIdDateCalculated = DateValidatorsUtils.stringToInt( request.getParameter( 
                            PARAMETER_ID_DATE_CALCULATED ) );

                if ( nIdDateCalculated == null )
                {
                    Object[] tabRequiredFields = 
                        {
                            I18nService.getLocalizedString( FIELD_RULE_DATE_CALCULATED, getLocale(  ) )
                        };

                    return AdminMessageService.getMessageUrl( request, MESSAGE_MANDATORY_FIELD, tabRequiredFields,
                        AdminMessage.TYPE_STOP );
                }

                // Checks if a rule already exists for this entry and this calculated date
                for ( Rule currentRule : RuleHome.findRulesByForm( rule.getIdForm(  ), PLUGIN ) )
                {
                    if ( ( currentRule.getIdRule(  ) != rule.getIdRule(  ) ) &&
                            ( currentRule.getIdEntry1(  ) == nIdEntry1 ) &&
                            ( currentRule.getDateCalculated(  ) != null ) &&
                            ( currentRule.getDateCalculated(  ).getIdDateCalculated(  ) == nIdDateCalculated ) )
                    {
                        return AdminMessageService.getMessageUrl( request,
                            MESSAGE_ERROR_RULE_ALREADY_EXISTS_DATE_CALCULATED, AdminMessage.TYPE_STOP );
                    }
                }

                DateCalculated dateCalculated = new DateCalculated(  );
                dateCalculated.setIdDateCalculated( nIdDateCalculated );
                rule.setDateCalculated( dateCalculated );

                break;

            default:
                return AdminMessageService.getMessageUrl( request, MESSAGE_ERROR_TYPE_NOT_EXISTS, AdminMessage.TYPE_STOP );
        }

        // No error
        return null;
    }

    /**
     * Gets the confirmation page for removing rule
     * @param request The HTTP request
     * @return The confirmation page for removing rule
     */
    public String getConfirmRemoveRule( HttpServletRequest request )
    {
        Integer nIdForm = DateValidatorsUtils.stringToInt( request.getParameter( 
                    DateValidatorsConstants.PARAMETER_ID_FORM ) );

        if ( !RBACService.isAuthorized( Rule.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    DateValidatorsResourceIdService.PERMISSION_RULE_DELETE, getUser(  ) ) )
        {
            return getJspManageValidator( request, nIdForm );
        }

        Integer nIdRule = DateValidatorsUtils.stringToInt( request.getParameter( PARAMETER_ID_RULE ) );

        if ( nIdRule == null )
        {
            return getJspManageValidator( request, nIdForm );
        }

        UrlItem url = new UrlItem( JSP_DO_REMOVE_RULE );
        url.addParameter( DateValidatorsConstants.PARAMETER_ID_FORM, nIdForm );
        url.addParameter( PARAMETER_ID_RULE, nIdRule );

        return AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_RULE, url.getUrl(  ),
            AdminMessage.TYPE_CONFIRMATION );
    }

    /**
     * Performs the rule removal
     *
     * @param request The HTTP request
     * @return The URL to go after performing the action
     */
    public String doRemoveRule( HttpServletRequest request )
    {
        Integer nIdForm = DateValidatorsUtils.stringToInt( request.getParameter( 
                    DateValidatorsConstants.PARAMETER_ID_FORM ) );

        if ( !RBACService.isAuthorized( Rule.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    DateValidatorsResourceIdService.PERMISSION_RULE_DELETE, getUser(  ) ) )
        {
            return getJspManageValidator( request, nIdForm );
        }

        Integer nIdRule = DateValidatorsUtils.stringToInt( request.getParameter( PARAMETER_ID_RULE ) );

        if ( nIdRule == null )
        {
            return getJspManageValidator( request, nIdForm );
        }

        RuleHome.remove( nIdRule, PLUGIN );

        return getJspManageValidator( request, nIdForm );
    }

    /**
     * Gets the calculated dates management page
     *
     * @param request The HTTP request
     * @return The calculated dates management page
     */
    public String getManageDateCalculated( HttpServletRequest request )
    {
        if ( !RBACService.isAuthorized( Rule.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    DateValidatorsResourceIdService.PERMISSION_DATE_CALCULATED_MANAGE, getUser(  ) ) )
        {
            return getManageValidator( request );
        }

        Integer nIdForm = DateValidatorsUtils.stringToInt( request.getParameter( 
                    DateValidatorsConstants.PARAMETER_ID_FORM ) );

        if ( nIdForm == null )
        {
            return getManageValidator( request );
        }

        setPageTitleProperty( PROPERTY_MANAGE_DATE_CALCULATED_TITLE );

        _nDefaultItemsPerPage = AppPropertiesService.getPropertyInt( DateValidatorsConstants.PROPERTY_ITEMS_PER_PAGE, 10 );
        _strCurrentPageIndex = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndex );
        _nItemsPerPage = Paginator.getItemsPerPage( request, Paginator.PARAMETER_ITEMS_PER_PAGE, _nItemsPerPage,
                _nDefaultItemsPerPage );

        List<DateCalculated> listDatesCalculated = DateCalculatedHome.findAll( PLUGIN );

        String strSortedAttributeName = request.getParameter( Parameters.SORTED_ATTRIBUTE_NAME );
        String strAscSort = null;

        if ( strSortedAttributeName != null )
        {
            strAscSort = request.getParameter( Parameters.SORTED_ASC );

            boolean bIsAscSort = Boolean.parseBoolean( strAscSort );

            Collections.sort( listDatesCalculated, new AttributeComparator( strSortedAttributeName, bIsAscSort ) );
        }

        UrlItem urlItem = new UrlItem( AppPathService.getBaseUrl( request ) + JSP_MANAGE_DATE_CALCULATED );
        urlItem.addParameter( DateValidatorsConstants.PARAMETER_ID_FORM, nIdForm );

        if ( strSortedAttributeName != null )
        {
            urlItem.addParameter( Parameters.SORTED_ATTRIBUTE_NAME, strSortedAttributeName );
        }

        if ( strAscSort != null )
        {
            urlItem.addParameter( Parameters.SORTED_ASC, strAscSort );
        }

        Paginator<DateCalculated> paginator = new Paginator<DateCalculated>( listDatesCalculated, _nItemsPerPage,
                urlItem.getUrl(  ), Paginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndex );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( DateValidatorsConstants.MARK_PAGINATOR, paginator );
        model.put( DateValidatorsConstants.MARK_NB_ITEMS_PER_PAGE, Integer.toString( _nItemsPerPage ) );
        model.put( MARK_DATE_CALCULATED_LIST, paginator.getPageItems(  ) );
        model.put( DateValidatorsConstants.MARK_ID_FORM, nIdForm );
        model.put( DateValidatorsConstants.MARK_SORTED_ATTRIBUTE_NAME, strSortedAttributeName );
        model.put( DateValidatorsConstants.MARK_SORTED_ASC, strAscSort );
        addPermissions( request, model );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MANAGE_DATE_CALCULATED, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
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
                    DateValidatorsResourceIdService.PERMISSION_DATE_CALCULATED_CREATE,
                    AdminUserService.getAdminUser( request ) ) )
        {
            bPermissionCreate = true;
        }

        model.put( DateValidatorsConstants.MARK_PERMISSION_CREATE, bPermissionCreate );

        // Modify
        boolean bPermissionModify = false;

        if ( RBACService.isAuthorized( Rule.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    DateValidatorsResourceIdService.PERMISSION_DATE_CALCULATED_MODIFY,
                    AdminUserService.getAdminUser( request ) ) )
        {
            bPermissionModify = true;
        }

        model.put( DateValidatorsConstants.MARK_PERMISSION_MODIFY, bPermissionModify );

        // Delete
        boolean bPermissionDelete = false;

        if ( RBACService.isAuthorized( Rule.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    DateValidatorsResourceIdService.PERMISSION_DATE_CALCULATED_DELETE,
                    AdminUserService.getAdminUser( request ) ) )
        {
            bPermissionDelete = true;
        }

        model.put( DateValidatorsConstants.MARK_PERMISSION_DELETE, bPermissionDelete );
    }

    /**
     * Gets the calculated date creation page
     *
     * @param request The HTTP request
     * @return The calculated date creation page
     */
    public String getCreateDateCalculated( HttpServletRequest request )
    {
        if ( !RBACService.isAuthorized( Rule.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    DateValidatorsResourceIdService.PERMISSION_DATE_CALCULATED_CREATE, getUser(  ) ) )
        {
            return getManageValidator( request );
        }

        Integer nIdForm = DateValidatorsUtils.stringToInt( request.getParameter( 
                    DateValidatorsConstants.PARAMETER_ID_FORM ) );

        if ( nIdForm == null )
        {
            return getManageValidator( request );
        }

        setPageTitleProperty( PROPERTY_CREATE_DATE_CALCULATED_TITLE );

        ReferenceList refListOperator = OperatorHome.findAll( PLUGIN );
        ReferenceItem refItem = new ReferenceItem(  );
        refItem.setCode( String.valueOf( -1 ) );
        refItem.setName( DateValidatorsConstants.EMPTY_STRING );
        refListOperator.add( 0, refItem );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_OPERATOR_LIST, refListOperator );
        model.put( MARK_UNIT_LIST, UnitHome.findAll( PLUGIN ) );
        model.put( DateValidatorsConstants.MARK_ID_FORM, nIdForm );
        model.put( MARK_LOCALE, request.getLocale(  ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_DATE_CALCULATED, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Performs the calculated date creation
     *
     * @param request The HTTP request
     * @return The URL to go after performing the action
     */
    public String doCreateDateCalculated( HttpServletRequest request )
    {
        Integer nIdForm = DateValidatorsUtils.stringToInt( request.getParameter( 
                    DateValidatorsConstants.PARAMETER_ID_FORM ) );

        if ( !RBACService.isAuthorized( Rule.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    DateValidatorsResourceIdService.PERMISSION_DATE_CALCULATED_CREATE, getUser(  ) ) )
        {
            return getJspManageValidator( request, nIdForm );
        }

        UrlItem url = new UrlItem( AppPathService.getBaseUrl( request ) + JSP_MANAGE_DATE_CALCULATED );
        url.addParameter( DateValidatorsConstants.PARAMETER_ID_FORM, nIdForm );

        // Cancel
        if ( StringUtils.isNotBlank( request.getParameter( PARAMETER_CANCEL ) ) )
        {
            return url.getUrl(  );
        }

        DateCalculated dateCalculated = new DateCalculated(  );

        // Get fields
        String strError = getDateCalculatedFields( request, dateCalculated );

        if ( strError != null )
        {
            return strError;
        }

        DateCalculatedHome.create( dateCalculated, PLUGIN );

        return url.getUrl(  );
    }

    /**
     * Gets the calculated date modification page
     *
     * @param request The HTTP request
     * @return The calculated date modification page
     */
    public String getModifyDateCalculated( HttpServletRequest request )
    {
        if ( !RBACService.isAuthorized( Rule.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    DateValidatorsResourceIdService.PERMISSION_DATE_CALCULATED_MODIFY, getUser(  ) ) )
        {
            return getManageValidator( request );
        }

        Integer nIdForm = DateValidatorsUtils.stringToInt( request.getParameter( 
                    DateValidatorsConstants.PARAMETER_ID_FORM ) );

        if ( nIdForm == null )
        {
            return getManageValidator( request );
        }

        Integer nIdDateCalculated = DateValidatorsUtils.stringToInt( request.getParameter( PARAMETER_ID_DATE_CALCULATED ) );

        if ( nIdDateCalculated == null )
        {
            return getManageValidator( request );
        }

        DateCalculated dateCalculated = DateCalculatedHome.findByPrimaryKey( nIdDateCalculated, PLUGIN );

        if ( dateCalculated == null )
        {
            return getManageValidator( request );
        }

        setPageTitleProperty( PROPERTY_MODIFY_DATE_CALCULATED_TITLE );

        ReferenceList refListOperator = OperatorHome.findAll( PLUGIN );
        ReferenceItem refItem = new ReferenceItem(  );
        refItem.setCode( String.valueOf( -1 ) );
        refItem.setName( DateValidatorsConstants.EMPTY_STRING );
        refListOperator.add( 0, refItem );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_DATE_CALCULATED, dateCalculated );
        model.put( MARK_OPERATOR_LIST, refListOperator );
        model.put( MARK_UNIT_LIST, UnitHome.findAll( PLUGIN ) );
        model.put( DateValidatorsConstants.MARK_ID_FORM, nIdForm );
        model.put( MARK_LOCALE, request.getLocale(  ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_DATE_CALCULATED, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Performs the calculated date modification
     *
     * @param request The HTTP request
     * @return The URL to go after performing the action
     */
    public String doModifyDateCalculated( HttpServletRequest request )
    {
        Integer nIdForm = DateValidatorsUtils.stringToInt( request.getParameter( 
                    DateValidatorsConstants.PARAMETER_ID_FORM ) );

        if ( !RBACService.isAuthorized( Rule.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    DateValidatorsResourceIdService.PERMISSION_DATE_CALCULATED_MODIFY, getUser(  ) ) )
        {
            return getJspManageValidator( request, nIdForm );
        }

        UrlItem url = new UrlItem( AppPathService.getBaseUrl( request ) + JSP_MANAGE_DATE_CALCULATED );
        url.addParameter( DateValidatorsConstants.PARAMETER_ID_FORM, nIdForm );

        // Cancel
        if ( StringUtils.isNotBlank( request.getParameter( PARAMETER_CANCEL ) ) )
        {
            return url.getUrl(  );
        }

        Integer nIdDateCalculated = DateValidatorsUtils.stringToInt( request.getParameter( PARAMETER_ID_DATE_CALCULATED ) );

        if ( nIdDateCalculated == null )
        {
            return getJspManageValidator( request, nIdForm );
        }

        DateCalculated dateCalculated = DateCalculatedHome.findByPrimaryKey( nIdDateCalculated, PLUGIN );

        if ( dateCalculated == null )
        {
            return getJspManageValidator( request, nIdForm );
        }

        // Get fields
        String strError = getDateCalculatedFields( request, dateCalculated );

        if ( strError != null )
        {
            return strError;
        }

        DateCalculatedHome.update( dateCalculated, PLUGIN );

        return url.getUrl(  );
    }

    /**
     * Gets the calculated date form fields
     *
     * @param request The HTTP request
     * @param dateCalculated The calculated date
     * @return Null if no error, else the AdminMessage URL
     */
    private String getDateCalculatedFields( HttpServletRequest request, DateCalculated dateCalculated )
    {
        Timestamp dateReference = DateUtil.formatTimestamp( request.getParameter( PARAMETER_DATE_REFERENCE ),
                request.getLocale(  ) );
        Integer nIdOperator = DateValidatorsUtils.stringToInt( request.getParameter( PARAMETER_ID_OPERATOR ) );
        Integer nNumber = DateValidatorsUtils.stringToInt( request.getParameter( PARAMETER_NUMBER ) );
        Integer nIdUnit = DateValidatorsUtils.stringToInt( request.getParameter( PARAMETER_ID_UNIT ) );

        String strFieldError = DateValidatorsConstants.EMPTY_STRING;

        if ( dateReference == null )
        {
            strFieldError = FIELD_DATE_CALCULATED_DATE_REFERENCE;
        }
        else if ( nIdOperator == null )
        {
            strFieldError = FIELD_DATE_CALCULATED_OPERATOR;
        }
        else if ( nIdUnit == null )
        {
            strFieldError = FIELD_DATE_CALCULATED_UNIT;
        }

        if ( StringUtils.isNotBlank( strFieldError ) )
        {
            Object[] tabRequiredFields = { I18nService.getLocalizedString( strFieldError, getLocale(  ) ) };

            return AdminMessageService.getMessageUrl( request, MESSAGE_MANDATORY_FIELD, tabRequiredFields,
                AdminMessage.TYPE_STOP );
        }

        // Number may be positive
        if ( ( nNumber == null ) || ( nNumber <= 0 ) )
        {
            Object[] tabRequiredFields = { I18nService.getLocalizedString( FIELD_DATE_CALCULATED_NUMBER, getLocale(  ) ) };

            return AdminMessageService.getMessageUrl( request, MESSAGE_NUMERIC_FIELD, tabRequiredFields,
                AdminMessage.TYPE_STOP );
        }

        // Checks if this calculated date already exists
        for ( DateCalculated currentDC : DateCalculatedHome.findAll( PLUGIN ) )
        {
            if ( ( currentDC.getIdDateCalculated(  ) != dateCalculated.getIdDateCalculated(  ) ) &&
                    currentDC.getDateReference(  ).equals( dateReference ) &&
                    ( currentDC.getOperator(  ).getIdOperator(  ) == nIdOperator ) &&
                    ( currentDC.getNumber(  ) == nNumber ) && ( currentDC.getUnit(  ).getIdUnit(  ) == nIdUnit ) )
            {
                return AdminMessageService.getMessageUrl( request, MESSAGE_ERROR_DATE_CALCULATED_ALREADY_EXISTS,
                    AdminMessage.TYPE_STOP );
            }
        }

        dateCalculated.setDateReference( dateReference );

        Operator operator = new Operator(  );
        operator.setIdOperator( nIdOperator );
        dateCalculated.setOperator( operator );

        dateCalculated.setNumber( nNumber );

        Unit unit = new Unit(  );
        unit.setIdUnit( nIdUnit );
        dateCalculated.setUnit( unit );

        // No error
        return null;
    }

    /**
     * Gets the confirmation page for removing calculated date
     * @param request The HTTP request
     * @return The confirmation page for removing calculated date
     */
    public String getConfirmRemoveDateCalculated( HttpServletRequest request )
    {
        Integer nIdForm = DateValidatorsUtils.stringToInt( request.getParameter( 
                    DateValidatorsConstants.PARAMETER_ID_FORM ) );

        if ( !RBACService.isAuthorized( Rule.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    DateValidatorsResourceIdService.PERMISSION_DATE_CALCULATED_DELETE, getUser(  ) ) )
        {
            return getJspManageValidator( request, nIdForm );
        }

        Integer nIdDateCalculated = DateValidatorsUtils.stringToInt( request.getParameter( PARAMETER_ID_DATE_CALCULATED ) );

        if ( nIdDateCalculated == null )
        {
            return getJspManageValidator( request, nIdForm );
        }

        UrlItem url = new UrlItem( JSP_DO_REMOVE_DATE_CALCULATED );
        url.addParameter( DateValidatorsConstants.PARAMETER_ID_FORM, nIdForm );
        url.addParameter( PARAMETER_ID_DATE_CALCULATED, nIdDateCalculated );

        return AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_DATE_CALCULATED, url.getUrl(  ),
            AdminMessage.TYPE_CONFIRMATION );
    }

    /**
     * Performs the calculated date removal
     *
     * @param request The HTTP request
     * @return The URL to go after performing the action
     */
    public String doRemoveDateCalculated( HttpServletRequest request )
    {
        Integer nIdForm = DateValidatorsUtils.stringToInt( request.getParameter( 
                    DateValidatorsConstants.PARAMETER_ID_FORM ) );

        if ( !RBACService.isAuthorized( Rule.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    DateValidatorsResourceIdService.PERMISSION_DATE_CALCULATED_DELETE, getUser(  ) ) )
        {
            return getJspManageValidator( request, nIdForm );
        }

        Integer nIdDateCalculated = DateValidatorsUtils.stringToInt( request.getParameter( PARAMETER_ID_DATE_CALCULATED ) );

        if ( nIdDateCalculated == null )
        {
            return getJspManageValidator( request, nIdForm );
        }

        DateCalculatedHome.remove( nIdDateCalculated, PLUGIN );

        UrlItem url = new UrlItem( AppPathService.getBaseUrl( request ) + JSP_MANAGE_DATE_CALCULATED );
        url.addParameter( DateValidatorsConstants.PARAMETER_ID_FORM, nIdForm );

        return url.getUrl(  );
    }
}
