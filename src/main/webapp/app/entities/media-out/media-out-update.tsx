import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './media-out.reducer';
import { IMediaOut } from 'app/shared/model/media-out.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMediaOutUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MediaOutUpdate = (props: IMediaOutUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { mediaOutEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/media-out');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...mediaOutEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dashboardApp.mediaOut.home.createOrEditLabel">
            <Translate contentKey="dashboardApp.mediaOut.home.createOrEditLabel">Create or edit a MediaOut</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : mediaOutEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="media-out-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="media-out-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="mediaOutIdLabel" for="media-out-mediaOutId">
                  <Translate contentKey="dashboardApp.mediaOut.mediaOutId">Media Out Id</Translate>
                </Label>
                <AvField id="media-out-mediaOutId" type="string" className="form-control" name="mediaOutId" />
              </AvGroup>
              <AvGroup>
                <Label id="mediaOutNameLabel" for="media-out-mediaOutName">
                  <Translate contentKey="dashboardApp.mediaOut.mediaOutName">Media Out Name</Translate>
                </Label>
                <AvField id="media-out-mediaOutName" type="text" name="mediaOutName" />
              </AvGroup>
              <AvGroup>
                <Label id="mediaOutTypeLabel" for="media-out-mediaOutType">
                  <Translate contentKey="dashboardApp.mediaOut.mediaOutType">Media Out Type</Translate>
                </Label>
                <AvInput
                  id="media-out-mediaOutType"
                  type="select"
                  className="form-control"
                  name="mediaOutType"
                  value={(!isNew && mediaOutEntity.mediaOutType) || 'PHOTO'}
                >
                  <option value="PHOTO">{translate('dashboardApp.MediaType.PHOTO')}</option>
                  <option value="VIDEO">{translate('dashboardApp.MediaType.VIDEO')}</option>
                  <option value="SOUND">{translate('dashboardApp.MediaType.SOUND')}</option>
                  <option value="MUSIC">{translate('dashboardApp.MediaType.MUSIC')}</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/media-out" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  mediaOutEntity: storeState.mediaOut.entity,
  loading: storeState.mediaOut.loading,
  updating: storeState.mediaOut.updating,
  updateSuccess: storeState.mediaOut.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MediaOutUpdate);
