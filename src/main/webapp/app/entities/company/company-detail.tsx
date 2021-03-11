import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './company.reducer';
import { ICompany } from 'app/shared/model/company.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICompanyDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CompanyDetail = (props: ICompanyDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { companyEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="dashboardApp.company.detail.title">Company</Translate> [<b>{companyEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="companyId">
              <Translate contentKey="dashboardApp.company.companyId">Company Id</Translate>
            </span>
          </dt>
          <dd>{companyEntity.companyId}</dd>
          <dt>
            <span id="resellerId">
              <Translate contentKey="dashboardApp.company.resellerId">Reseller Id</Translate>
            </span>
          </dt>
          <dd>{companyEntity.resellerId}</dd>
          <dt>
            <span id="companyName">
              <Translate contentKey="dashboardApp.company.companyName">Company Name</Translate>
            </span>
          </dt>
          <dd>{companyEntity.companyName}</dd>
          <dt>
            <span id="billingAdress1">
              <Translate contentKey="dashboardApp.company.billingAdress1">Billing Adress 1</Translate>
            </span>
          </dt>
          <dd>{companyEntity.billingAdress1}</dd>
          <dt>
            <span id="billingAdress2">
              <Translate contentKey="dashboardApp.company.billingAdress2">Billing Adress 2</Translate>
            </span>
          </dt>
          <dd>{companyEntity.billingAdress2}</dd>
          <dt>
            <span id="billingZip">
              <Translate contentKey="dashboardApp.company.billingZip">Billing Zip</Translate>
            </span>
          </dt>
          <dd>{companyEntity.billingZip}</dd>
          <dt>
            <span id="billingPlace">
              <Translate contentKey="dashboardApp.company.billingPlace">Billing Place</Translate>
            </span>
          </dt>
          <dd>{companyEntity.billingPlace}</dd>
          <dt>
            <span id="billingCountry">
              <Translate contentKey="dashboardApp.company.billingCountry">Billing Country</Translate>
            </span>
          </dt>
          <dd>{companyEntity.billingCountry}</dd>
          <dt>
            <span id="billingEmail">
              <Translate contentKey="dashboardApp.company.billingEmail">Billing Email</Translate>
            </span>
          </dt>
          <dd>{companyEntity.billingEmail}</dd>
          <dt>
            <span id="billingPhone">
              <Translate contentKey="dashboardApp.company.billingPhone">Billing Phone</Translate>
            </span>
          </dt>
          <dd>{companyEntity.billingPhone}</dd>
          <dt>
            <span id="shippingAdress1">
              <Translate contentKey="dashboardApp.company.shippingAdress1">Shipping Adress 1</Translate>
            </span>
          </dt>
          <dd>{companyEntity.shippingAdress1}</dd>
          <dt>
            <span id="shippingAdress2">
              <Translate contentKey="dashboardApp.company.shippingAdress2">Shipping Adress 2</Translate>
            </span>
          </dt>
          <dd>{companyEntity.shippingAdress2}</dd>
          <dt>
            <span id="shippingZip">
              <Translate contentKey="dashboardApp.company.shippingZip">Shipping Zip</Translate>
            </span>
          </dt>
          <dd>{companyEntity.shippingZip}</dd>
          <dt>
            <span id="shippingPlace">
              <Translate contentKey="dashboardApp.company.shippingPlace">Shipping Place</Translate>
            </span>
          </dt>
          <dd>{companyEntity.shippingPlace}</dd>
          <dt>
            <span id="shippingCountry">
              <Translate contentKey="dashboardApp.company.shippingCountry">Shipping Country</Translate>
            </span>
          </dt>
          <dd>{companyEntity.shippingCountry}</dd>
          <dt>
            <span id="shippingEmail">
              <Translate contentKey="dashboardApp.company.shippingEmail">Shipping Email</Translate>
            </span>
          </dt>
          <dd>{companyEntity.shippingEmail}</dd>
          <dt>
            <span id="shippingPhone">
              <Translate contentKey="dashboardApp.company.shippingPhone">Shipping Phone</Translate>
            </span>
          </dt>
          <dd>{companyEntity.shippingPhone}</dd>
          <dt>
            <span id="vatPercentage">
              <Translate contentKey="dashboardApp.company.vatPercentage">Vat Percentage</Translate>
            </span>
          </dt>
          <dd>{companyEntity.vatPercentage}</dd>
          <dt>
            <span id="vatNumber">
              <Translate contentKey="dashboardApp.company.vatNumber">Vat Number</Translate>
            </span>
          </dt>
          <dd>{companyEntity.vatNumber}</dd>
          <dt>
            <span id="companyCreatedAt">
              <Translate contentKey="dashboardApp.company.companyCreatedAt">Company Created At</Translate>
            </span>
          </dt>
          <dd>
            {companyEntity.companyCreatedAt ? (
              <TextFormat value={companyEntity.companyCreatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="companyModifiedAt">
              <Translate contentKey="dashboardApp.company.companyModifiedAt">Company Modified At</Translate>
            </span>
          </dt>
          <dd>
            {companyEntity.companyModifiedAt ? (
              <TextFormat value={companyEntity.companyModifiedAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="dashboardApp.company.reseller">Reseller</Translate>
          </dt>
          <dd>{companyEntity.reseller ? companyEntity.reseller.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/company" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/company/${companyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ company }: IRootState) => ({
  companyEntity: company.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CompanyDetail);
