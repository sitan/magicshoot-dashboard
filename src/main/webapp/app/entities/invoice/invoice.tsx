import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './invoice.reducer';
import { IInvoice } from 'app/shared/model/invoice.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IInvoiceProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Invoice = (props: IInvoiceProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { invoiceList, match, loading } = props;
  return (
    <div>
      <h2 id="invoice-heading">
        <Translate contentKey="dashboardApp.invoice.home.title">Invoices</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="dashboardApp.invoice.home.createLabel">Create new Invoice</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {invoiceList && invoiceList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.invoice.invoiceId">Invoice Id</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.invoice.contactId">Contact Id</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.invoice.companyId">Company Id</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.invoice.invoiceNumber">Invoice Number</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.invoice.orderNumber">Order Number</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.invoice.orderDateTime">Order Date Time</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.invoice.createdAt">Created At</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.invoice.modifiedAt">Modified At</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.invoice.quote">Quote</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {invoiceList.map((invoice, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${invoice.id}`} color="link" size="sm">
                      {invoice.id}
                    </Button>
                  </td>
                  <td>{invoice.invoiceId}</td>
                  <td>{invoice.contactId}</td>
                  <td>{invoice.companyId}</td>
                  <td>{invoice.invoiceNumber}</td>
                  <td>{invoice.orderNumber}</td>
                  <td>{invoice.orderDateTime}</td>
                  <td>{invoice.createdAt ? <TextFormat type="date" value={invoice.createdAt} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>
                    {invoice.modifiedAt ? <TextFormat type="date" value={invoice.modifiedAt} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{invoice.quote ? <Link to={`quote/${invoice.quote.id}`}>{invoice.quote.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${invoice.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${invoice.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${invoice.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="dashboardApp.invoice.home.notFound">No Invoices found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ invoice }: IRootState) => ({
  invoiceList: invoice.entities,
  loading: invoice.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Invoice);
