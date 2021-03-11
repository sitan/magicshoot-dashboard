import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './print.reducer';
import { IPrint } from 'app/shared/model/print.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPrintProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Print = (props: IPrintProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { printList, match, loading } = props;
  return (
    <div>
      <h2 id="print-heading">
        <Translate contentKey="dashboardApp.print.home.title">Prints</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="dashboardApp.print.home.createLabel">Create new Print</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {printList && printList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.print.printId">Print Id</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.print.printName">Print Name</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.print.printType">Print Type</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.print.printWidth">Print Width</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.print.printHeight">Print Height</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {printList.map((print, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${print.id}`} color="link" size="sm">
                      {print.id}
                    </Button>
                  </td>
                  <td>{print.printId}</td>
                  <td>{print.printName}</td>
                  <td>
                    <Translate contentKey={`dashboardApp.PrintType.${print.printType}`} />
                  </td>
                  <td>{print.printWidth}</td>
                  <td>{print.printHeight}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${print.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${print.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${print.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="dashboardApp.print.home.notFound">No Prints found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ print }: IRootState) => ({
  printList: print.entities,
  loading: print.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Print);
