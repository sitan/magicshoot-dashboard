import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './device.reducer';
import { IDevice } from 'app/shared/model/device.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDeviceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DeviceUpdate = (props: IDeviceUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { deviceEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/device');
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
        ...deviceEntity,
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
          <h2 id="dashboardApp.device.home.createOrEditLabel">
            <Translate contentKey="dashboardApp.device.home.createOrEditLabel">Create or edit a Device</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : deviceEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="device-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="device-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="deviceIdLabel" for="device-deviceId">
                  <Translate contentKey="dashboardApp.device.deviceId">Device Id</Translate>
                </Label>
                <AvField id="device-deviceId" type="string" className="form-control" name="deviceId" />
              </AvGroup>
              <AvGroup>
                <Label id="deviceNameLabel" for="device-deviceName">
                  <Translate contentKey="dashboardApp.device.deviceName">Device Name</Translate>
                </Label>
                <AvField id="device-deviceName" type="text" name="deviceName" />
              </AvGroup>
              <AvGroup>
                <Label id="deviceTypeLabel" for="device-deviceType">
                  <Translate contentKey="dashboardApp.device.deviceType">Device Type</Translate>
                </Label>
                <AvInput
                  id="device-deviceType"
                  type="select"
                  className="form-control"
                  name="deviceType"
                  value={(!isNew && deviceEntity.deviceType) || 'CAMERA'}
                >
                  <option value="CAMERA">{translate('dashboardApp.DeviceType.CAMERA')}</option>
                  <option value="PAYTER">{translate('dashboardApp.DeviceType.PAYTER')}</option>
                  <option value="LABELPRINTER">{translate('dashboardApp.DeviceType.LABELPRINTER')}</option>
                  <option value="PHOTOPRINTER">{translate('dashboardApp.DeviceType.PHOTOPRINTER')}</option>
                  <option value="TOUCHSCREEN">{translate('dashboardApp.DeviceType.TOUCHSCREEN')}</option>
                  <option value="TV40INCH">{translate('dashboardApp.DeviceType.TV40INCH')}</option>
                  <option value="PC">{translate('dashboardApp.DeviceType.PC')}</option>
                  <option value="MODEM">{translate('dashboardApp.DeviceType.MODEM')}</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/device" replace color="info">
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
  deviceEntity: storeState.device.entity,
  loading: storeState.device.loading,
  updating: storeState.device.updating,
  updateSuccess: storeState.device.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DeviceUpdate);
