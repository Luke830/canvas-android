/// Copyright (C) 2019 - present Instructure, Inc.
///
/// This program is free software: you can redistribute it and/or modify
/// it under the terms of the GNU General Public License as published by
/// the Free Software Foundation, version 3 of the License.
///
/// This program is distributed in the hope that it will be useful,
/// but WITHOUT ANY WARRANTY; without even the implied warranty of
/// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
/// GNU General Public License for more details.
///
/// You should have received a copy of the GNU General Public License
/// along with this program.  If not, see <http://www.gnu.org/licenses/>.

import 'package:built_collection/built_collection.dart';
import 'package:built_value/built_value.dart';
import 'package:built_value/serializer.dart';

part 'alert.g.dart';

/// To have this built_value be generated, run this command from the project root:
/// flutter packages pub run build_runner build --delete-conflicting-outputs
abstract class Alert implements Built<Alert, AlertBuilder> {
  @BuiltValueSerializer(serializeNulls: true) // Add this line to get nulls to serialize when we convert to JSON
  static Serializer<Alert> get serializer => _$alertSerializer;

  Alert._();

  factory Alert([void Function(AlertBuilder) updates]) = _$Alert;

  int get id;

  @BuiltValueField(wireName: "observer_alert_threshold_id")
  int get observerAlertThresholdId;

  @BuiltValueField(wireName: "context_type")
  String get contextType;

  @BuiltValueField(wireName: "context_id")
  int get contextId;

  @BuiltValueField(wireName: "alert_type")
  AlertType get alertType;

  @BuiltValueField(wireName: "workflow_state")
  AlertWorkflowState get workflowState;

  @BuiltValueField(wireName: "action_date")
  DateTime get actionDate;

  String get title;

  @BuiltValueField(wireName: "user_id")
  int get userId;

  @BuiltValueField(wireName: "observer_id")
  int get observerId;

  @BuiltValueField(wireName: "html_url")
  String get htmlUrl;

  static void _initializeBuilder(AlertBuilder b) => b
    ..id = 0
    ..observerAlertThresholdId = 0
    ..contextType = 'institution'
    ..contextId = 0
    ..alertType = AlertType.institutionAnnouncement
    ..workflowState = AlertWorkflowState.unread
    ..actionDate = DateTime.now()
    ..title = ''
    ..userId = 0
    ..observerId = 0
    ..htmlUrl = '';

  // Utility functions

  bool isAlertInfo() {
    return const [
      AlertType.courseAnnouncement,
      AlertType.institutionAnnouncement,
    ].contains(alertType);
  }

  bool isAlertPositive() {
    return const [
      AlertType.assignmentGradeHigh,
      AlertType.courseGradeHigh,
    ].contains(alertType);
  }

  bool isAlertNegative() {
    return const [
      AlertType.assignmentMissing,
      AlertType.assignmentGradeLow,
      AlertType.courseGradeLow,
    ].contains(alertType);
  }
}

/// If you need to change the values sent over the wire when serializing you
/// can do so using the [BuiltValueEnum] and [BuiltValueEnumConst] annotations.
@BuiltValueEnum(wireName: 'alert_type')
class AlertType extends EnumClass {
  const AlertType._(String name) : super(name);

  static BuiltSet<AlertType> get values => _$alertTypeValues;

  static AlertType valueOf(String name) => _$alertTypeValueOf(name);

  static Serializer<AlertType> get serializer => _$alertTypeSerializer;

  @BuiltValueEnumConst(wireName: 'assignment_missing')
  static const AlertType assignmentMissing = _$alertTypeAssignmentMissing;

  @BuiltValueEnumConst(wireName: 'assignment_grade_high')
  static const AlertType assignmentGradeHigh = _$alertTypeAssignmentGradeHigh;

  @BuiltValueEnumConst(wireName: 'assignment_grade_low')
  static const AlertType assignmentGradeLow = _$alertTypeAssignmentGradeLow;

  @BuiltValueEnumConst(wireName: 'course_grade_high')
  static const AlertType courseGradeHigh = _$alertTypeCourseGradeHigh;

  @BuiltValueEnumConst(wireName: 'course_grade_low')
  static const AlertType courseGradeLow = _$alertTypeCourseGradeLow;

  @BuiltValueEnumConst(wireName: 'course_announcement')
  static const AlertType courseAnnouncement = _$alertTypeCourseAnnouncement;

  @BuiltValueEnumConst(wireName: 'institution_announcement')
  static const AlertType institutionAnnouncement = _$alertTypeInstitutionAnnouncement;

  @BuiltValueEnumConst(fallback: true)
  static const AlertType unknown = _$alertTypeUnknown;
}

@BuiltValueEnum(wireName: 'workflow_state')
class AlertWorkflowState extends EnumClass {
  const AlertWorkflowState._(String name) : super(name);

  static BuiltSet<AlertWorkflowState> get values => _$alertWorkflowStateValues;

  static AlertWorkflowState valueOf(String name) => _$alertWorkflowStateValueOf(name);

  static Serializer<AlertWorkflowState> get serializer => _$alertWorkflowStateSerializer;

  static const AlertWorkflowState read = _$alertWorkflowStateRead;

  static const AlertWorkflowState unread = _$alertWorkflowStateUnread;

  static const AlertWorkflowState deleted = _$alertWorkflowStateDeletted;

  static const AlertWorkflowState dismissed = _$alertWorkflowStateDismissed;

  @BuiltValueEnumConst(fallback: true)
  static const AlertWorkflowState unknown = _$alertWorkflowStateUnknown;
}