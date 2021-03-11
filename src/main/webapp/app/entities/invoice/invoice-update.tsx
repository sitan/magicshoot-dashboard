import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IQuote } from 'app/shared/model/quote.model';
import { getEntities as getQuotes } from 'app/entities/quote/quote.reducer';
import { getEntity, updateEntity, createEntity, reset } from './invoice.reducer';
import { IInvoice } from 'app/shared/model/invoice.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IInvoiceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InvoiceUpdate = (props: IInvoiceUpdateProps) => {
  const [quoteId, setQuoteId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { invoiceEntity, quotes, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/invoice');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getQuotes();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...invoiceEntity,
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
          <h2 id="dashboardApp.invoice.home.createOrEditLabel">
            <Translate contentKey="dashboardApp.invoice.home.createOrEditLabel">Create or edit a Invoice</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : invoiceEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="invoice-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="invoice-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="invoiceIdLabel" for="invoice-invoiceId">
                  <Translate contentKey="dashboardApp.invoice.invoiceId">Invoice Id</Translate>
                </Label>
                <AvField id="invoice-invoiceId" type="string" className="form-control" name="invoiceId" />
              </AvGroup>
              <AvGroup>
                <Label id="contactIdLabel" for="invoice-contactId">
                  <Translate contentKey="dashboardApp.invoice.contactId">Contact Id</Translate>
                </Label>
                <AvField id="invoice-contactId" type="string" className="form-control" name="contactId" />
              </AvGroup>
              <AvGroup>
                <Label id="companyIdLabel" for="invoice-companyId">
                  <Translate contentKey="dashboardApp.invoice.companyId">Company Id</Translate>
                </Label>
                <AvField id="invoice-companyId" type="string" className="form-control" name="companyId" />
              </AvGroup>
              <AvGroup>
                <Label id="invoiceNumberLabel" for="invoice-invoiceNumber">
                  <Translate contentKey="dashboardApp.invoice.invoiceNumber">Invoice Number</Translate>
                </Label>
                <AvField id="invoice-invoiceNumber" type="text" name="invoiceNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="orderNumberLabel" for="invoice-orderNumber">
                  <Translate contentKey="dashboardApp.invoice.orderNumber">Order Number</Translate>
                </Label>
                <AvField id="invoice-orderNumber" type="text" name="orderNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="orderDateTimeLabel" for="invoice-orderDateTime">
                  <Translate contentKey="dashboardApp.invoice.orderDateTime">Order Date Time</Translate>
                </Label>
                <AvField id="invoice-orderDateTime" type="text" name="orderDateTime" />
              </AvGroup>
              <AvGroup>
                <Label id="createdAtLabel" for="invoice-createdAt">
                  <Translate contentKey="dashboardApp.invoice.createdAt">Created At</Translate>
                </Label>
                <AvField id="invoice-createdAt" type="date" className="form-control" name="createdAt" />
              </AvGroup>
              <AvGroup>
                <Label id="modifiedAtLabel" for="invoice-modifiedAt">
                  <Translate contentKey="dashboardApp.invoice.modifiedAt">Modified At</Translate>
                </Label>
                <AvField id="invoice-modifiedAt" type="date" className="form-control" name="modifiedAt" />
              </AvGroup>
              <AvGroup>
                <Label for="invoice-quote">
                  <Translate contentKey="dashboardApp.invoice.quote">Quote</Translate>
                </Label>
                <AvInput id="invoice-quote" type="select" className="form-control" name="quote.id">
                  <option value="" key="0" />
                  {quotes
                    ? quotes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/invoice" replace color="info">
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
  quotes: storeState.quote.entities,
  invoiceEntity: storeState.invoice.entity,
  loading: storeState.invoice.loading,
  updating: storeState.invoice.updating,
  updateSuccess: storeState.invoice.updateSuccess,
});

const mapDispatchToProps = {
  getQuotes,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InvoiceUpdate);
