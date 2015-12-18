INSERT INTO form_date_validators_comparator(id_comparator,name,class) VALUES(1, '=', 'fr.paris.lutece.plugins.form.modules.datevalidators.business.comparator.ComparatorEqual');
INSERT INTO form_date_validators_comparator(id_comparator,name,class) VALUES(2, '!=', 'fr.paris.lutece.plugins.form.modules.datevalidators.business.comparator.ComparatorNotEqual');
INSERT INTO form_date_validators_comparator(id_comparator,name,class) VALUES(3, '>', 'fr.paris.lutece.plugins.form.modules.datevalidators.business.comparator.ComparatorGreater');
INSERT INTO form_date_validators_comparator(id_comparator,name,class) VALUES(4, '>=', 'fr.paris.lutece.plugins.form.modules.datevalidators.business.comparator.ComparatorGreaterOrEqual');
INSERT INTO form_date_validators_comparator(id_comparator,name,class) VALUES(5, '<', 'fr.paris.lutece.plugins.form.modules.datevalidators.business.comparator.ComparatorLess');
INSERT INTO form_date_validators_comparator(id_comparator,name,class) VALUES(6, '<=', 'fr.paris.lutece.plugins.form.modules.datevalidators.business.comparator.ComparatorLessOrEqual');

INSERT INTO form_date_validators_operator(id_operator,name,class) VALUES(1, '+', 'fr.paris.lutece.plugins.form.modules.datevalidators.business.operator.OperatorAdd');
INSERT INTO form_date_validators_operator(id_operator,name,class) VALUES(2, '-', 'fr.paris.lutece.plugins.form.modules.datevalidators.business.operator.OperatorSubstract');
-- INSERT INTO form_date_validators_operator(id_operator,name,class) VALUES(3, '*', 'fr.paris.lutece.plugins.form.modules.datevalidators.business.operator.OperatorMultiply');
-- INSERT INTO form_date_validators_operator(id_operator,name,class) VALUES(4, '/', 'fr.paris.lutece.plugins.form.modules.datevalidators.business.operator.OperatorDivide');

INSERT INTO form_date_validators_unit(id_unit,name,class) VALUES(1, 'module.form.datevalidators.unit.label.year', 'fr.paris.lutece.plugins.form.modules.datevalidators.business.unit.UnitYear');
INSERT INTO form_date_validators_unit(id_unit,name,class) VALUES(2, 'module.form.datevalidators.unit.label.month', 'fr.paris.lutece.plugins.form.modules.datevalidators.business.unit.UnitMonth');
INSERT INTO form_date_validators_unit(id_unit,name,class) VALUES(3, 'module.form.datevalidators.unit.label.day', 'fr.paris.lutece.plugins.form.modules.datevalidators.business.unit.UnitDay');
