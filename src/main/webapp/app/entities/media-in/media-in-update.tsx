import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './media-in.reducer';
import { IMediaIn } from 'app/shared/model/media-in.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMediaInUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MediaInUpdate = (props: IMediaInUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { mediaInEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/media-in');
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
        ...mediaInEntity,
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
          <h2 id="dashboardApp.mediaIn.home.createOrEditLabel">
            <Translate contentKey="dashboardApp.mediaIn.home.createOrEditLabel">Create or edit a MediaIn</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : mediaInEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="media-in-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="media-in-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="mediaInIdLabel" for="media-in-mediaInId">
                  <Translate contentKey="dashboardApp.mediaIn.mediaInId">Media In Id</Translate>
                </Label>
                <AvField id="media-in-mediaInId" type="string" className="form-control" name="mediaInId" />
              </AvGroup>
              <AvGroup>
                <Label id="mediaInNameLabel" for="media-in-mediaInName">
                  <Translate contentKey="dashboardApp.mediaIn.mediaInName">Media In Name</Translate>
                </Label>
                <AvField id="media-in-mediaInName" type="text" name="mediaInName" />
              </AvGroup>
              <AvGroup>
                <Label id="mediaUseLabel" for="media-in-mediaUse">
                  <Translate contentKey="dashboardApp.mediaIn.mediaUse">Media Use</Translate>
                </Label>
                <AvInput
                  id="media-in-mediaUse"
                  type="select"
                  className="form-control"
                  name="mediaUse"
                  value={(!isNew && mediaInEntity.mediaUse) || 'FOREGROUND'}
                >
                  <option value="FOREGROUND">{translate('dashboardApp.MediaUse.FOREGROUND')}</option>
                  <option value="BACKGROUND">{translate('dashboardApp.MediaUse.BACKGROUND')}</option>
                  <option value="HANDPROP">{translate('dashboardApp.MediaUse.HANDPROP')}</option>
                  <option value="FACEPROP">{translate('dashboardApp.MediaUse.FACEPROP')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="mediaInTypeLabel" for="media-in-mediaInType">
                  <Translate contentKey="dashboardApp.mediaIn.mediaInType">Media In Type</Translate>
                </Label>
                <AvInput
                  id="media-in-mediaInType"
                  type="select"
                  className="form-control"
                  name="mediaInType"
                  value={(!isNew && mediaInEntity.mediaInType) || 'PHOTO'}
                >
                  <option value="PHOTO">{translate('dashboardApp.MediaType.PHOTO')}</option>
                  <option value="VIDEO">{translate('dashboardApp.MediaType.VIDEO')}</option>
                  <option value="SOUND">{translate('dashboardApp.MediaType.SOUND')}</option>
                  <option value="MUSIC">{translate('dashboardApp.MediaType.MUSIC')}</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/media-in" replace color="info">
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
  mediaInEntity: storeState.mediaIn.entity,
  loading: storeState.mediaIn.loading,
  updating: storeState.mediaIn.updating,
  updateSuccess: storeState.mediaIn.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MediaInUpdate);
