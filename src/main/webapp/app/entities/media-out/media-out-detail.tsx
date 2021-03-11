import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './media-out.reducer';
import { IMediaOut } from 'app/shared/model/media-out.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMediaOutDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MediaOutDetail = (props: IMediaOutDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { mediaOutEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="dashboardApp.mediaOut.detail.title">MediaOut</Translate> [<b>{mediaOutEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="mediaOutId">
              <Translate contentKey="dashboardApp.mediaOut.mediaOutId">Media Out Id</Translate>
            </span>
          </dt>
          <dd>{mediaOutEntity.mediaOutId}</dd>
          <dt>
            <span id="mediaOutName">
              <Translate contentKey="dashboardApp.mediaOut.mediaOutName">Media Out Name</Translate>
            </span>
          </dt>
          <dd>{mediaOutEntity.mediaOutName}</dd>
          <dt>
            <span id="mediaOutType">
              <Translate contentKey="dashboardApp.mediaOut.mediaOutType">Media Out Type</Translate>
            </span>
          </dt>
          <dd>{mediaOutEntity.mediaOutType}</dd>
        </dl>
        <Button tag={Link} to="/media-out" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/media-out/${mediaOutEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ mediaOut }: IRootState) => ({
  mediaOutEntity: mediaOut.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MediaOutDetail);
