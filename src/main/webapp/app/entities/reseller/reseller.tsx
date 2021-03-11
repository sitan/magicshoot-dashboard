import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './reseller.reducer';
import { IReseller } from 'app/shared/model/reseller.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IResellerProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Reseller = (props: IResellerProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { resellerList, match, loading } = props;
  return (
    <div>
      <h2 id="reseller-heading">
        <Translate contentKey="dashboardApp.reseller.home.title">Resellers</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="dashboardApp.reseller.home.createLabel">Create new Reseller</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {resellerList && resellerList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.reseller.resellerId">Reseller Id</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.reseller.adminId">Admin Id</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.reseller.resellerName">Reseller Name</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.reseller.resellerEmail">Reseller Email</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.reseller.resellerPassword">Reseller Password</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.reseller.resellerCreatedAt">Reseller Created At</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.reseller.resellerModifiedAt">Reseller Modified At</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.reseller.admin">Admin</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {resellerList.map((reseller, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${reseller.id}`} color="link" size="sm">
                      {reseller.id}
                    </Button>
                  </td>
                  <td>{reseller.resellerId}</td>
                  <td>{reseller.adminId}</td>
                  <td>{reseller.resellerName}</td>
                  <td>{reseller.resellerEmail}</td>
                  <td>{reseller.resellerPassword}</td>
                  <td>
                    {reseller.resellerCreatedAt ? (
                      <TextFormat type="date" value={reseller.resellerCreatedAt} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {reseller.resellerModifiedAt ? (
                      <TextFormat type="date" value={reseller.resellerModifiedAt} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{reseller.admin ? <Link to={`admin/${reseller.admin.id}`}>{reseller.admin.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${reseller.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${reseller.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${reseller.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="dashboardApp.reseller.home.notFound">No Resellers found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ reseller }: IRootState) => ({
  resellerList: reseller.entities,
  loading: reseller.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Reseller);
