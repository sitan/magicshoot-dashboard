import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAdmin } from 'app/shared/model/admin.model';
import { getEntities as getAdmins } from 'app/entities/admin/admin.reducer';
import { getEntity, updateEntity, createEntity, reset } from './reseller.reducer';
import { IReseller } from 'app/shared/model/reseller.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IResellerUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ResellerUpdate = (props: IResellerUpdateProps) => {
  const [adminId, setAdminId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { resellerEntity, admins, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/reseller');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getAdmins();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...resellerEntity,
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
          <h2 id="dashboardApp.reseller.home.createOrEditLabel">
            <Translate contentKey="dashboardApp.reseller.home.createOrEditLabel">Create or edit a Reseller</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : resellerEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="reseller-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="reseller-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="resellerIdLabel" for="reseller-resellerId">
                  <Translate contentKey="dashboardApp.reseller.resellerId">Reseller Id</Translate>
                </Label>
                <AvField id="reseller-resellerId" type="string" className="form-control" name="resellerId" />
              </AvGroup>
              <AvGroup>
                <Label id="adminIdLabel" for="reseller-adminId">
                  <Translate contentKey="dashboardApp.reseller.adminId">Admin Id</Translate>
                </Label>
                <AvField id="reseller-adminId" type="string" className="form-control" name="adminId" />
              </AvGroup>
              <AvGroup>
                <Label id="resellerNameLabel" for="reseller-resellerName">
                  <Translate contentKey="dashboardApp.reseller.resellerName">Reseller Name</Translate>
                </Label>
                <AvField id="reseller-resellerName" type="text" name="resellerName" />
              </AvGroup>
              <AvGroup>
                <Label id="resellerEmailLabel" for="reseller-resellerEmail">
                  <Translate contentKey="dashboardApp.reseller.resellerEmail">Reseller Email</Translate>
                </Label>
                <AvField id="reseller-resellerEmail" type="text" name="resellerEmail" />
              </AvGroup>
              <AvGroup>
                <Label id="resellerPasswordLabel" for="reseller-resellerPassword">
                  <Translate contentKey="dashboardApp.reseller.resellerPassword">Reseller Password</Translate>
                </Label>
                <AvField id="reseller-resellerPassword" type="text" name="resellerPassword" />
              </AvGroup>
              <AvGroup>
                <Label id="resellerCreatedAtLabel" for="reseller-resellerCreatedAt">
                  <Translate contentKey="dashboardApp.reseller.resellerCreatedAt">Reseller Created At</Translate>
                </Label>
                <AvField id="reseller-resellerCreatedAt" type="date" className="form-control" name="resellerCreatedAt" />
              </AvGroup>
              <AvGroup>
                <Label id="resellerModifiedAtLabel" for="reseller-resellerModifiedAt">
                  <Translate contentKey="dashboardApp.reseller.resellerModifiedAt">Reseller Modified At</Translate>
                </Label>
                <AvField id="reseller-resellerModifiedAt" type="date" className="form-control" name="resellerModifiedAt" />
              </AvGroup>
              <AvGroup>
                <Label for="reseller-admin">
                  <Translate contentKey="dashboardApp.reseller.admin">Admin</Translate>
                </Label>
                <AvInput id="reseller-admin" type="select" className="form-control" name="admin.id">
                  <option value="" key="0" />
                  {admins
                    ? admins.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/reseller" replace color="info">
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
  admins: storeState.admin.entities,
  resellerEntity: storeState.reseller.entity,
  loading: storeState.reseller.loading,
  updating: storeState.reseller.updating,
  updateSuccess: storeState.reseller.updateSuccess,
});

const mapDispatchToProps = {
  getAdmins,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ResellerUpdate);
