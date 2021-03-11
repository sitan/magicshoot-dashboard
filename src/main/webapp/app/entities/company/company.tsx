import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './company.reducer';
import { ICompany } from 'app/shared/model/company.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICompanyProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Company = (props: ICompanyProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { companyList, match, loading } = props;
  return (
    <div>
      <h2 id="company-heading">
        <Translate contentKey="dashboardApp.company.home.title">Companies</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="dashboardApp.company.home.createLabel">Create new Company</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {companyList && companyList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.company.companyId">Company Id</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.company.resellerId">Reseller Id</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.company.companyName">Company Name</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.company.billingAdress1">Billing Adress 1</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.company.billingAdress2">Billing Adress 2</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.company.billingZip">Billing Zip</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.company.billingPlace">Billing Place</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.company.billingCountry">Billing Country</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.company.billingEmail">Billing Email</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.company.billingPhone">Billing Phone</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.company.shippingAdress1">Shipping Adress 1</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.company.shippingAdress2">Shipping Adress 2</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.company.shippingZip">Shipping Zip</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.company.shippingPlace">Shipping Place</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.company.shippingCountry">Shipping Country</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.company.shippingEmail">Shipping Email</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.company.shippingPhone">Shipping Phone</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.company.vatPercentage">Vat Percentage</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.company.vatNumber">Vat Number</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.company.companyCreatedAt">Company Created At</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.company.companyModifiedAt">Company Modified At</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.company.reseller">Reseller</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {companyList.map((company, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${company.id}`} color="link" size="sm">
                      {company.id}
                    </Button>
                  </td>
                  <td>{company.companyId}</td>
                  <td>{company.resellerId}</td>
                  <td>{company.companyName}</td>
                  <td>{company.billingAdress1}</td>
                  <td>{company.billingAdress2}</td>
                  <td>{company.billingZip}</td>
                  <td>{company.billingPlace}</td>
                  <td>{company.billingCountry}</td>
                  <td>{company.billingEmail}</td>
                  <td>{company.billingPhone}</td>
                  <td>{company.shippingAdress1}</td>
                  <td>{company.shippingAdress2}</td>
                  <td>{company.shippingZip}</td>
                  <td>{company.shippingPlace}</td>
                  <td>{company.shippingCountry}</td>
                  <td>{company.shippingEmail}</td>
                  <td>{company.shippingPhone}</td>
                  <td>{company.vatPercentage}</td>
                  <td>{company.vatNumber}</td>
                  <td>
                    {company.companyCreatedAt ? (
                      <TextFormat type="date" value={company.companyCreatedAt} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {company.companyModifiedAt ? (
                      <TextFormat type="date" value={company.companyModifiedAt} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{company.reseller ? <Link to={`reseller/${company.reseller.id}`}>{company.reseller.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${company.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${company.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${company.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="dashboardApp.company.home.notFound">No Companies found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ company }: IRootState) => ({
  companyList: company.entities,
  loading: company.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Company);
