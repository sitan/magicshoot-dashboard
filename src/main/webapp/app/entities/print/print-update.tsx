import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './print.reducer';
import { IPrint } from 'app/shared/model/print.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPrintUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PrintUpdate = (props: IPrintUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { printEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/print');
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
        ...printEntity,
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
          <h2 id="dashboardApp.print.home.createOrEditLabel">
            <Translate contentKey="dashboardApp.print.home.createOrEditLabel">Create or edit a Print</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : printEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="print-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="print-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="printIdLabel" for="print-printId">
                  <Translate contentKey="dashboardApp.print.printId">Print Id</Translate>
                </Label>
                <AvField id="print-printId" type="string" className="form-control" name="printId" />
              </AvGroup>
              <AvGroup>
                <Label id="printNameLabel" for="print-printName">
                  <Translate contentKey="dashboardApp.print.printName">Print Name</Translate>
                </Label>
                <AvField id="print-printName" type="text" name="printName" />
              </AvGroup>
              <AvGroup>
                <Label id="printTypeLabel" for="print-printType">
                  <Translate contentKey="dashboardApp.print.printType">Print Type</Translate>
                </Label>
                <AvInput
                  id="print-printType"
                  type="select"
                  className="form-control"
                  name="printType"
                  value={(!isNew && printEntity.printType) || 'GLANZEND'}
                >
                  <option value="GLANZEND">{translate('dashboardApp.PrintType.GLANZEND')}</option>
                  <option value="MAT">{translate('dashboardApp.PrintType.MAT')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="printWidthLabel" for="print-printWidth">
                  <Translate contentKey="dashboardApp.print.printWidth">Print Width</Translate>
                </Label>
                <AvField id="print-printWidth" type="string" className="form-control" name="printWidth" />
              </AvGroup>
              <AvGroup>
                <Label id="printHeightLabel" for="print-printHeight">
                  <Translate contentKey="dashboardApp.print.printHeight">Print Height</Translate>
                </Label>
                <AvField id="print-printHeight" type="string" className="form-control" name="printHeight" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/print" replace color="info">
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
  printEntity: storeState.print.entity,
  loading: storeState.print.loading,
  updating: storeState.print.updating,
  updateSuccess: storeState.print.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PrintUpdate);
