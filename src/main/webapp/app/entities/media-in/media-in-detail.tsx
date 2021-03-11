import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './media-in.reducer';
import { IMediaIn } from 'app/shared/model/media-in.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMediaInDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MediaInDetail = (props: IMediaInDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { mediaInEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="dashboardApp.mediaIn.detail.title">MediaIn</Translate> [<b>{mediaInEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="mediaInId">
              <Translate contentKey="dashboardApp.mediaIn.mediaInId">Media In Id</Translate>
            </span>
          </dt>
          <dd>{mediaInEntity.mediaInId}</dd>
          <dt>
            <span id="mediaInName">
              <Translate contentKey="dashboardApp.mediaIn.mediaInName">Media In Name</Translate>
            </span>
          </dt>
          <dd>{mediaInEntity.mediaInName}</dd>
          <dt>
            <span id="mediaUse">
              <Translate contentKey="dashboardApp.mediaIn.mediaUse">Media Use</Translate>
            </span>
          </dt>
          <dd>{mediaInEntity.mediaUse}</dd>
          <dt>
            <span id="mediaInType">
              <Translate contentKey="dashboardApp.mediaIn.mediaInType">Media In Type</Translate>
            </span>
          </dt>
          <dd>{mediaInEntity.mediaInType}</dd>
        </dl>
        <Button tag={Link} to="/media-in" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/media-in/${mediaInEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ mediaIn }: IRootState) => ({
  mediaInEntity: mediaIn.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MediaInDetail);
