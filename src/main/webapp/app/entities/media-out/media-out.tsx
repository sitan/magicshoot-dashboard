import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './media-out.reducer';
import { IMediaOut } from 'app/shared/model/media-out.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMediaOutProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const MediaOut = (props: IMediaOutProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { mediaOutList, match, loading } = props;
  return (
    <div>
      <h2 id="media-out-heading">
        <Translate contentKey="dashboardApp.mediaOut.home.title">Media Outs</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="dashboardApp.mediaOut.home.createLabel">Create new Media Out</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {mediaOutList && mediaOutList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.mediaOut.mediaOutId">Media Out Id</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.mediaOut.mediaOutName">Media Out Name</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.mediaOut.mediaOutType">Media Out Type</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {mediaOutList.map((mediaOut, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${mediaOut.id}`} color="link" size="sm">
                      {mediaOut.id}
                    </Button>
                  </td>
                  <td>{mediaOut.mediaOutId}</td>
                  <td>{mediaOut.mediaOutName}</td>
                  <td>
                    <Translate contentKey={`dashboardApp.MediaType.${mediaOut.mediaOutType}`} />
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${mediaOut.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${mediaOut.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${mediaOut.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="dashboardApp.mediaOut.home.notFound">No Media Outs found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ mediaOut }: IRootState) => ({
  mediaOutList: mediaOut.entities,
  loading: mediaOut.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MediaOut);
