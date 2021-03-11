import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IQuote, defaultValue } from 'app/shared/model/quote.model';

export const ACTION_TYPES = {
  FETCH_QUOTE_LIST: 'quote/FETCH_QUOTE_LIST',
  FETCH_QUOTE: 'quote/FETCH_QUOTE',
  CREATE_QUOTE: 'quote/CREATE_QUOTE',
  UPDATE_QUOTE: 'quote/UPDATE_QUOTE',
  DELETE_QUOTE: 'quote/DELETE_QUOTE',
  RESET: 'quote/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IQuote>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type QuoteState = Readonly<typeof initialState>;

// Reducer

export default (state: QuoteState = initialState, action): QuoteState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_QUOTE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_QUOTE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_QUOTE):
    case REQUEST(ACTION_TYPES.UPDATE_QUOTE):
    case REQUEST(ACTION_TYPES.DELETE_QUOTE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_QUOTE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_QUOTE):
    case FAILURE(ACTION_TYPES.CREATE_QUOTE):
    case FAILURE(ACTION_TYPES.UPDATE_QUOTE):
    case FAILURE(ACTION_TYPES.DELETE_QUOTE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_QUOTE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_QUOTE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_QUOTE):
    case SUCCESS(ACTION_TYPES.UPDATE_QUOTE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_QUOTE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/quotes';

// Actions

export const getEntities: ICrudGetAllAction<IQuote> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_QUOTE_LIST,
  payload: axios.get<IQuote>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IQuote> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_QUOTE,
    payload: axios.get<IQuote>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IQuote> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_QUOTE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IQuote> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_QUOTE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IQuote> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_QUOTE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
