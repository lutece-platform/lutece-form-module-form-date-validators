/*
 * Copyright (c) 2002-2014, Mairie de Paris
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
package fr.paris.lutece.plugins.form.modules.datevalidators.util;


/**
 * DateValidatorsConstants
 */
public final class DateValidatorsConstants
{
    // Markers
    public static final String MARK_PAGINATOR = "paginator";
    public static final String MARK_NB_ITEMS_PER_PAGE = "nb_items_per_page";
    public static final String MARK_ID_FORM = "id_form";
    public static final String MARK_ENTRY_LIST = "entry_list";
    public static final String MARK_COMPARATOR_LIST = "comparator_list";
    public static final String MARK_SORTED_ATTRIBUTE_NAME = "sorted_attribute_name";
    public static final String MARK_SORTED_ASC = "asc_sort";
    public static final String MARK_PERMISSION_CREATE = "permission_create";
    public static final String MARK_PERMISSION_MODIFY = "permission_modify";
    public static final String MARK_PERMISSION_DELETE = "permission_delete";

    // Types
    public static final int TYPE_ENTRY = 1;
    public static final int TYPE_DATE_REFERENCE = 2;
    public static final int TYPE_DATE_CALCULATED = 3;

    // Constants
    public static final String EMPTY_STRING = "";
    public static final String COMMA = ",";

    // Parameter
    public static final String PARAMETER_ID_FORM = "id_form";

    // Property
    public static final String PROPERTY_ITEMS_PER_PAGE = "form-date-validators.paginator.itemsPerPage";

    // JSP
    public static final String JSP_MANAGE_VALIDATOR = "jsp/admin/plugins/form/ManageValidator.jsp";

    /**
     * Private constructor
     */
    private DateValidatorsConstants(  )
    {
    }
}
