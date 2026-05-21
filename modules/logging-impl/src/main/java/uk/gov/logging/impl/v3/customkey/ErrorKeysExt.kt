package uk.gov.logging.impl.v3.customkey

import uk.gov.logging.api.v3.customkey.CustomKey
import uk.gov.logging.api.v3.customkey.ErrorKeys

private const val COMPONENT_KEY = "err_component"
private const val ACTION_KEY = "err_action"
private const val DEFAULT_STRING_VALUE = ""

internal fun ErrorKeys.componentKey() = CustomKey.StringKey(COMPONENT_KEY, component ?: DEFAULT_STRING_VALUE)

internal fun ErrorKeys.actionKey() = CustomKey.StringKey(ACTION_KEY, action ?: DEFAULT_STRING_VALUE)
