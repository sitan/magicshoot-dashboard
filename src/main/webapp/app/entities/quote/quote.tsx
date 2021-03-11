import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './quote.reducer';
import { IQuote } from 'app/shared/model/quote.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IQuoteProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Quote = (props: IQuoteProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { quoteList, match, loading } = props;
  return (
    <div>
      <h2 id="quote-heading">
        <Translate contentKey="dashboardApp.quote.home.title">Quotes</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="dashboardApp.quote.home.createLabel">Create new Quote</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {quoteList && quoteList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.quote.quoteId">Quote Id</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.quote.contactId">Contact Id</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.quote.companyId">Company Id</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.quote.quoteDescription">Quote Description</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.quote.quotePrice">Quote Price</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.quote.orderNumber">Order Number</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.quote.quoteCreatedAt">Quote Created At</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.quote.quoteModifiedAt">Quote Modified At</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.quote.contact">Contact</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {quoteList.map((quote, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${quote.id}`} color="link" size="sm">
                      {quote.id}
                    </Button>
                  </td>
                  <td>{quote.quoteId}</td>
                  <td>{quote.contactId}</td>
                  <td>{quote.companyId}</td>
                  <td>{quote.quoteDescription}</td>
                  <td>{quote.quotePrice}</td>
                  <td>{quote.orderNumber}</td>
                  <td>
                    {quote.quoteCreatedAt ? <TextFormat type="date" value={quote.quoteCreatedAt} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {quote.quoteModifiedAt ? <TextFormat type="date" value={quote.quoteModifiedAt} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{quote.contact ? <Link to={`contact/${quote.contact.id}`}>{quote.contact.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${quote.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${quote.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${quote.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="dashboardApp.quote.home.notFound">No Quotes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ quote }: IRootState) => ({
  quoteList: quote.entities,
  loading: quote.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Quote);
