import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './event.reducer';
import { IEvent } from 'app/shared/model/event.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEventProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Event = (props: IEventProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { eventList, match, loading } = props;
  return (
    <div>
      <h2 id="event-heading">
        <Translate contentKey="dashboardApp.event.home.title">Events</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="dashboardApp.event.home.createLabel">Create new Event</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {eventList && eventList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.event.eventId">Event Id</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.event.companyId">Company Id</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.event.eventName">Event Name</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.event.eventStartDate">Event Start Date</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.event.eventEndDate">Event End Date</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.event.eventStartTime">Event Start Time</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.event.eventEndTime">Event End Time</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.event.eventType">Event Type</Translate>
                </th>
                <th>
                  <Translate contentKey="dashboardApp.event.company">Company</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {eventList.map((event, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${event.id}`} color="link" size="sm">
                      {event.id}
                    </Button>
                  </td>
                  <td>{event.eventId}</td>
                  <td>{event.companyId}</td>
                  <td>{event.eventName}</td>
                  <td>
                    {event.eventStartDate ? <TextFormat type="date" value={event.eventStartDate} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {event.eventEndDate ? <TextFormat type="date" value={event.eventEndDate} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {event.eventStartTime ? <TextFormat type="date" value={event.eventStartTime} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {event.eventEndTime ? <TextFormat type="date" value={event.eventEndTime} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    <Translate contentKey={`dashboardApp.EventType.${event.eventType}`} />
                  </td>
                  <td>{event.company ? <Link to={`company/${event.company.id}`}>{event.company.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${event.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${event.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${event.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="dashboardApp.event.home.notFound">No Events found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ event }: IRootState) => ({
  eventList: event.entities,
  loading: event.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Event);
