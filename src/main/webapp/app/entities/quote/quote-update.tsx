import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IContact } from 'app/shared/model/contact.model';
import { getEntities as getContacts } from 'app/entities/contact/contact.reducer';
import { getEntity, updateEntity, createEntity, reset } from './quote.reducer';
import { IQuote } from 'app/shared/model/quote.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IQuoteUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const QuoteUpdate = (props: IQuoteUpdateProps) => {
  const [contactId, setContactId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { quoteEntity, contacts, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/quote');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getContacts();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...quoteEntity,
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
          <h2 id="dashboardApp.quote.home.createOrEditLabel">
            <Translate contentKey="dashboardApp.quote.home.createOrEditLabel">Create or edit a Quote</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : quoteEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="quote-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="quote-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="quoteIdLabel" for="quote-quoteId">
                  <Translate contentKey="dashboardApp.quote.quoteId">Quote Id</Translate>
                </Label>
                <AvField id="quote-quoteId" type="string" className="form-control" name="quoteId" />
              </AvGroup>
              <AvGroup>
                <Label id="contactIdLabel" for="quote-contactId">
                  <Translate contentKey="dashboardApp.quote.contactId">Contact Id</Translate>
                </Label>
                <AvField id="quote-contactId" type="string" className="form-control" name="contactId" />
              </AvGroup>
              <AvGroup>
                <Label id="companyIdLabel" for="quote-companyId">
                  <Translate contentKey="dashboardApp.quote.companyId">Company Id</Translate>
                </Label>
                <AvField id="quote-companyId" type="string" className="form-control" name="companyId" />
              </AvGroup>
              <AvGroup>
                <Label id="quoteDescriptionLabel" for="quote-quoteDescription">
                  <Translate contentKey="dashboardApp.quote.quoteDescription">Quote Description</Translate>
                </Label>
                <AvField id="quote-quoteDescription" type="text" name="quoteDescription" />
              </AvGroup>
              <AvGroup>
                <Label id="quotePriceLabel" for="quote-quotePrice">
                  <Translate contentKey="dashboardApp.quote.quotePrice">Quote Price</Translate>
                </Label>
                <AvField id="quote-quotePrice" type="string" className="form-control" name="quotePrice" />
              </AvGroup>
              <AvGroup>
                <Label id="orderNumberLabel" for="quote-orderNumber">
                  <Translate contentKey="dashboardApp.quote.orderNumber">Order Number</Translate>
                </Label>
                <AvField id="quote-orderNumber" type="text" name="orderNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="quoteCreatedAtLabel" for="quote-quoteCreatedAt">
                  <Translate contentKey="dashboardApp.quote.quoteCreatedAt">Quote Created At</Translate>
                </Label>
                <AvField id="quote-quoteCreatedAt" type="date" className="form-control" name="quoteCreatedAt" />
              </AvGroup>
              <AvGroup>
                <Label id="quoteModifiedAtLabel" for="quote-quoteModifiedAt">
                  <Translate contentKey="dashboardApp.quote.quoteModifiedAt">Quote Modified At</Translate>
                </Label>
                <AvField id="quote-quoteModifiedAt" type="date" className="form-control" name="quoteModifiedAt" />
              </AvGroup>
              <AvGroup>
                <Label for="quote-contact">
                  <Translate contentKey="dashboardApp.quote.contact">Contact</Translate>
                </Label>
                <AvInput id="quote-contact" type="select" className="form-control" name="contact.id">
                  <option value="" key="0" />
                  {contacts
                    ? contacts.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/quote" replace color="info">
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
  contacts: storeState.contact.entities,
  quoteEntity: storeState.quote.entity,
  loading: storeState.quote.loading,
  updating: storeState.quote.updating,
  updateSuccess: storeState.quote.updateSuccess,
});

const mapDispatchToProps = {
  getContacts,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(QuoteUpdate);
