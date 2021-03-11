import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './label.reducer';
import { ILabel } from 'app/shared/model/label.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ILabelUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const LabelUpdate = (props: ILabelUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { labelEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/label');
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
        ...labelEntity,
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
          <h2 id="dashboardApp.label.home.createOrEditLabel">
            <Translate contentKey="dashboardApp.label.home.createOrEditLabel">Create or edit a Label</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : labelEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="label-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="label-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="labelIdLabel" for="label-labelId">
                  <Translate contentKey="dashboardApp.label.labelId">Label Id</Translate>
                </Label>
                <AvField id="label-labelId" type="string" className="form-control" name="labelId" />
              </AvGroup>
              <AvGroup>
                <Label id="labelNameLabel" for="label-labelName">
                  <Translate contentKey="dashboardApp.label.labelName">Label Name</Translate>
                </Label>
                <AvField id="label-labelName" type="text" name="labelName" />
              </AvGroup>
              <AvGroup>
                <Label id="labelTypeLabel" for="label-labelType">
                  <Translate contentKey="dashboardApp.label.labelType">Label Type</Translate>
                </Label>
                <AvInput
                  id="label-labelType"
                  type="select"
                  className="form-control"
                  name="labelType"
                  value={(!isNew && labelEntity.labelType) || 'THERMISCH'}
                >
                  <option value="THERMISCH">{translate('dashboardApp.LabelType.THERMISCH')}</option>
                  <option value="INKT">{translate('dashboardApp.LabelType.INKT')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="labelWidthLabel" for="label-labelWidth">
                  <Translate contentKey="dashboardApp.label.labelWidth">Label Width</Translate>
                </Label>
                <AvField id="label-labelWidth" type="string" className="form-control" name="labelWidth" />
              </AvGroup>
              <AvGroup>
                <Label id="labelHeightLabel" for="label-labelHeight">
                  <Translate contentKey="dashboardApp.label.labelHeight">Label Height</Translate>
                </Label>
                <AvField id="label-labelHeight" type="string" className="form-control" name="labelHeight" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/label" replace color="info">
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
  labelEntity: storeState.label.entity,
  loading: storeState.label.loading,
  updating: storeState.label.updating,
  updateSuccess: storeState.label.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(LabelUpdate);
