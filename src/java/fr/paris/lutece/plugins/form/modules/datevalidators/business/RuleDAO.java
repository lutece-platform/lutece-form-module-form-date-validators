/*
 * Copyright (c) 2002-2010, Mairie de Paris
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
package fr.paris.lutece.plugins.form.modules.datevalidators.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;


/**
 * RuleDAO
 */
public class RuleDAO implements IRuleDAO
{
    private static final String SQL_QUERY_NEW_PK = "SELECT max(id_rule) FROM form_date_validators_rule";
    private static final String SQL_QUERY_INSERT = "INSERT INTO form_date_validators_rule(id_rule,id_form,id_entry_1,id_comparator,id_entry_2,date_reference,id_date_calculated) VALUES(?,?,?,?,?,?,?)";
    private static final String SQL_QUERY_UPDATE = "UPDATE form_date_validators_rule SET id_entry_1=?,id_comparator=?,id_entry_2=?,date_reference=?,id_date_calculated=? WHERE id_rule=?";
    private static final String SQL_QUERY_DELETE = "DELETE FROM form_date_validators_rule WHERE id_rule=?";
    private static final String SQL_QUERY_FIND_BY_PRIMARY_KEY = "SELECT id_form,id_entry_1,id_comparator,id_entry_2,date_reference,id_date_calculated FROM form_date_validators_rule WHERE id_rule=?";
    private static final String SQL_QUERY_SELECT_BY_FORM = "SELECT rule.id_rule, rule.id_entry_1, rule.id_comparator, rule.id_entry_2, rule.date_reference, rule.id_date_calculated, " +
        "dc.date_reference, dc.id_operator, operator.name, dc.number, dc.id_unit, unit.name FROM form_date_validators_rule rule " +
        "LEFT JOIN form_date_validators_date_calculated dc ON (rule.id_date_calculated = dc.id_date_calculated) " +
        "LEFT JOIN form_date_validators_operator operator ON (dc.id_operator = operator.id_operator) " +
        "LEFT JOIN form_date_validators_unit unit ON (dc.id_unit = unit.id_unit) " + "WHERE rule.id_form = ?";

    /**
    * Generates a new primary key
    *
    * @param plugin The plugin
    * @return The new primary key
    */
    private int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, plugin );
        daoUtil.executeQuery(  );

        int nKey;

        if ( !daoUtil.next(  ) )
        {
            // If the table is empty
            nKey = 1;
        }

        nKey = daoUtil.getInt( 1 ) + 1;

        daoUtil.free(  );

        return nKey;
    }

    /**
     * Creates a new instance of rule
     *
     * @param rule The instance of rule which contains the informations to store
     * @param plugin The plugin
     */
    public void insert( Rule rule, Plugin plugin )
    {
        rule.setIdRule( newPrimaryKey( plugin ) );

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, rule.getIdRule(  ) );
        daoUtil.setInt( nIndex++, rule.getIdForm(  ) );
        daoUtil.setInt( nIndex++, rule.getIdEntry1(  ) );
        daoUtil.setInt( nIndex++, rule.getIdComparator(  ) );

        if ( rule.getIdEntry2(  ) != null )
        {
            daoUtil.setInt( nIndex++, rule.getIdEntry2(  ) );
        }
        else
        {
            daoUtil.setIntNull( nIndex++ );
        }

        if ( rule.getDateReference(  ) != null )
        {
            daoUtil.setDate( nIndex++, new Date( rule.getDateReference(  ).getTime(  ) ) );
        }
        else
        {
            daoUtil.setDate( nIndex++, null );
        }

        if ( ( rule.getDateCalculated(  ) != null ) && ( rule.getDateCalculated(  ).getIdDateCalculated(  ) > 0 ) )
        {
            daoUtil.setInt( nIndex++, rule.getDateCalculated(  ).getIdDateCalculated(  ) );
        }
        else
        {
            daoUtil.setIntNull( nIndex++ );
        }

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Updates the rule whose identifier is specified in parameter
     *
     * @param rule The instance of rule which contains the informations to update
     * @param plugin The plugin
     */
    public void store( Rule rule, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, rule.getIdEntry1(  ) );
        daoUtil.setInt( nIndex++, rule.getIdComparator(  ) );

        if ( rule.getIdEntry2(  ) != null )
        {
            daoUtil.setInt( nIndex++, rule.getIdEntry2(  ) );
        }
        else
        {
            daoUtil.setIntNull( nIndex++ );
        }

        if ( rule.getDateReference(  ) != null )
        {
            daoUtil.setDate( nIndex++, new Date( rule.getDateReference(  ).getTime(  ) ) );
        }
        else
        {
            daoUtil.setDate( nIndex++, null );
        }

        if ( ( rule.getDateCalculated(  ) != null ) && ( rule.getDateCalculated(  ).getIdDateCalculated(  ) > 0 ) )
        {
            daoUtil.setInt( nIndex++, rule.getDateCalculated(  ).getIdDateCalculated(  ) );
        }
        else
        {
            daoUtil.setIntNull( nIndex++ );
        }

        daoUtil.setInt( nIndex++, rule.getIdRule(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Removes the rule whose identifier is specified in parameter
     *
     * @param nKey The rule primary key
     * @param plugin The plugin
     */
    public void delete( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nKey );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Returns the rule whose identifier is specified in parameter
     *
     * @param nKey The rule primary key
     * @param plugin The plugin
     * @return The rule whose identifier is specified in parameter
     */
    public Rule load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_BY_PRIMARY_KEY, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery(  );

        Rule rule = null;

        if ( daoUtil.next(  ) )
        {
            rule = new Rule(  );
            rule.setIdRule( nKey );
            rule.setIdForm( daoUtil.getInt( 1 ) );
            rule.setIdEntry1( daoUtil.getInt( 2 ) );
            rule.setIdComparator( daoUtil.getInt( 3 ) );

            if ( daoUtil.getObject( 4 ) != null )
            {
                rule.setIdEntry2( daoUtil.getInt( 4 ) );
            }

            rule.setDateReference( daoUtil.getDate( 5 ) );

            if ( daoUtil.getObject( 6 ) != null )
            {
                DateCalculated dateCalculated = new DateCalculated(  );
                dateCalculated.setIdDateCalculated( daoUtil.getInt( 6 ) );
                rule.setDateCalculated( dateCalculated );
            }
        }

        daoUtil.free(  );

        return rule;
    }

    /**
     * Finds all rules for a given form identifier
     *
     * @param nIdForm the form identifier
     * @param plugin The plugin
     * @return the list which contains the found rules
     */
    public List<Rule> selectRulesByForm( int nIdForm, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_FORM, plugin );
        daoUtil.setInt( 1, nIdForm );
        daoUtil.executeQuery(  );

        List<Rule> listRules = new ArrayList<Rule>(  );
        Rule rule;

        while ( daoUtil.next(  ) )
        {
            rule = new Rule(  );
            rule.setIdRule( daoUtil.getInt( 1 ) );
            rule.setIdForm( nIdForm );
            rule.setIdEntry1( daoUtil.getInt( 2 ) );
            rule.setIdComparator( daoUtil.getInt( 3 ) );

            if ( daoUtil.getObject( 4 ) != null )
            {
                rule.setIdEntry2( daoUtil.getInt( 4 ) );
            }

            rule.setDateReference( daoUtil.getDate( 5 ) );

            if ( daoUtil.getObject( 6 ) != null )
            {
                DateCalculated dateCalculated = new DateCalculated(  );
                dateCalculated.setIdDateCalculated( daoUtil.getInt( 6 ) );
                dateCalculated.setDateReference( daoUtil.getTimestamp( 7 ) );

                Operator operator = new Operator(  );
                operator.setIdOperator( daoUtil.getInt( 8 ) );
                operator.setName( daoUtil.getString( 9 ) );
                dateCalculated.setOperator( operator );

                dateCalculated.setNumber( daoUtil.getInt( 10 ) );

                Unit unit = new Unit(  );
                unit.setIdUnit( daoUtil.getInt( 11 ) );
                unit.setName( daoUtil.getString( 12 ) );
                dateCalculated.setUnit( unit );

                rule.setDateCalculated( dateCalculated );
            }

            listRules.add( rule );
        }

        daoUtil.free(  );

        return listRules;
    }
}
