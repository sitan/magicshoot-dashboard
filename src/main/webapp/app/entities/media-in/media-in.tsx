import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './media-in.reducer';
import { IMediaIn } from 'app/shared/model/media-in.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMediaInProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const MediaIn = (props: IMediaInProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { mediaInList, match, loading } = props;
  return (
    <div>
      <h2 id="media-in-heading">
        <Translate contentKey="dashboardApp.mediaIn.home.title">Media Ins</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="dashboardApp.mediaIn.home.createLabel">Create new Media In</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {mediaInList && mediaInList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.mediaIn.mediaInId">Media In Id</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.mediaIn.mediaInName">Media In Name</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.mediaIn.mediaUse">Media Use</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.mediaIn.mediaInType">Media In Type</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {mediaInList.map((mediaIn, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${mediaIn.id}`} color="link" size="sm">
                      {mediaIn.id}
                    </Button>
                  </td>
                  <td>{mediaIn.mediaInId}</td>
                  <td>{mediaIn.mediaInName}</td>
                  <td>
                    <Translate contentKey={`dashboardApp.MediaUse.${mediaIn.mediaUse}`} />
                  </td>
                  <td>
                    <Translate contentKey={`dashboardApp.MediaType.${mediaIn.mediaInType}`} />
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${mediaIn.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${mediaIn.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${mediaIn.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="dashboardApp.mediaIn.home.notFound">No Media Ins found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ mediaIn }: IRootState) => ({
  mediaInList: mediaIn.entities,
  loading: mediaIn.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MediaIn);
