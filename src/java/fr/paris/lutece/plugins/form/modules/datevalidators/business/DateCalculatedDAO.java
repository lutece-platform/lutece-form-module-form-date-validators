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
package fr.paris.lutece.plugins.form.modules.datevalidators.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;


/**
 * DateCalculatedDAO
 */
public class DateCalculatedDAO implements IDateCalculatedDAO
{
    private static final String SQL_QUERY_NEW_PK = "SELECT max(id_date_calculated) FROM form_date_validators_date_calculated";
    private static final String SQL_QUERY_INSERT = "INSERT INTO form_date_validators_date_calculated(id_date_calculated,date_reference,id_operator,number,id_unit) VALUES(?,?,?,?,?)";
    private static final String SQL_QUERY_UPDATE = "UPDATE form_date_validators_date_calculated SET date_reference=?,id_operator=?,number=?,id_unit=? WHERE id_date_calculated=?";
    private static final String SQL_QUERY_DELETE = "DELETE FROM form_date_validators_date_calculated WHERE id_date_calculated=?";
    private static final String SQL_QUERY_FIND_BY_PRIMARY_KEY = "SELECT dc.date_reference, dc.id_operator, operator.name, dc.number, dc.id_unit, unit.name " +
        "FROM form_date_validators_date_calculated dc " +
        "INNER JOIN form_date_validators_operator operator ON (dc.id_operator = operator.id_operator) " +
        "INNER JOIN form_date_validators_unit unit ON (dc.id_unit = unit.id_unit)" + "WHERE dc.id_date_calculated = ?";
    private static final String SQL_QUERY_SELECT_ALL = "SELECT dc.id_date_calculated, dc.date_reference, dc.id_operator, operator.name, dc.number, dc.id_unit, unit.name " +
        "FROM form_date_validators_date_calculated dc " +
        "INNER JOIN form_date_validators_operator operator ON (dc.id_operator = operator.id_operator) " +
        "INNER JOIN form_date_validators_unit unit ON (dc.id_unit = unit.id_unit)";

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
     * Creates a new instance of calculated date
     *
     * @param dateCalculated The instance of calculated date which contains the informations to store
     * @param plugin The plugin
     */
    public void insert( DateCalculated dateCalculated, Plugin plugin )
    {
        dateCalculated.setIdDateCalculated( newPrimaryKey( plugin ) );

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, dateCalculated.getIdDateCalculated(  ) );
        daoUtil.setDate( nIndex++, new Date( dateCalculated.getDateReference(  ).getTime(  ) ) );
        daoUtil.setInt( nIndex++, dateCalculated.getOperator(  ).getIdOperator(  ) );
        daoUtil.setInt( nIndex++, dateCalculated.getNumber(  ) );
        daoUtil.setInt( nIndex++, dateCalculated.getUnit(  ).getIdUnit(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Updates the calculated date whose identifier is specified in parameter
     *
     * @param dateCalculated The instance of calculated date which contains the informations to update
     * @param plugin The plugin
     */
    public void store( DateCalculated dateCalculated, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;

        daoUtil.setDate( nIndex++, new Date( dateCalculated.getDateReference(  ).getTime(  ) ) );
        daoUtil.setInt( nIndex++, dateCalculated.getOperator(  ).getIdOperator(  ) );
        daoUtil.setInt( nIndex++, dateCalculated.getNumber(  ) );
        daoUtil.setInt( nIndex++, dateCalculated.getUnit(  ).getIdUnit(  ) );

        daoUtil.setInt( nIndex++, dateCalculated.getIdDateCalculated(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Removes the calculated date whose identifier is specified in parameter
     *
     * @param nKey The calculated date primary key
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
     * Returns the calculated date whose identifier is specified in parameter
     *
     * @param nKey The calculated date primary key
     * @param plugin The plugin
     * @return The calculated date whose identifier is specified in parameter
     */
    public DateCalculated load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_BY_PRIMARY_KEY, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery(  );

        DateCalculated dateCalculated = null;

        if ( daoUtil.next(  ) )
        {
            int nIndex = 1;

            dateCalculated = new DateCalculated(  );
            dateCalculated.setIdDateCalculated( nKey );
            dateCalculated.setDateReference( daoUtil.getTimestamp( nIndex++ ) );

            Operator operator = new Operator(  );
            operator.setIdOperator( daoUtil.getInt( nIndex++ ) );
            operator.setName( daoUtil.getString( nIndex++ ) );
            dateCalculated.setOperator( operator );

            dateCalculated.setNumber( daoUtil.getInt( nIndex++ ) );

            Unit unit = new Unit(  );
            unit.setIdUnit( daoUtil.getInt( nIndex++ ) );
            unit.setName( daoUtil.getString( nIndex++ ) );
            dateCalculated.setUnit( unit );
        }

        daoUtil.free(  );

        return dateCalculated;
    }

    /**
     * Finds all calculated dates
     *
     * @param plugin The plugin
     * @return the list which contains all calculated dates
     */
    public List<DateCalculated> selectAll( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_ALL, plugin );
        daoUtil.executeQuery(  );

        List<DateCalculated> listDatesCalculated = new ArrayList<DateCalculated>(  );
        DateCalculated dateCalculated;
        Operator operator;
        Unit unit;
        int nIndex;

        while ( daoUtil.next(  ) )
        {
            nIndex = 1;

            dateCalculated = new DateCalculated(  );
            dateCalculated.setIdDateCalculated( daoUtil.getInt( nIndex++ ) );
            dateCalculated.setDateReference( daoUtil.getTimestamp( nIndex++ ) );

            operator = new Operator(  );
            operator.setIdOperator( daoUtil.getInt( nIndex++ ) );
            operator.setName( daoUtil.getString( nIndex++ ) );
            dateCalculated.setOperator( operator );

            dateCalculated.setNumber( daoUtil.getInt( nIndex++ ) );

            unit = new Unit(  );
            unit.setIdUnit( daoUtil.getInt( nIndex++ ) );
            unit.setName( daoUtil.getString( nIndex++ ) );
            dateCalculated.setUnit( unit );

            listDatesCalculated.add( dateCalculated );
        }

        daoUtil.free(  );

        return listDatesCalculated;
    }
}
