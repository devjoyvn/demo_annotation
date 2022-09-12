import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProcessAnnotation {

    private void sanitizerObject(Object object) throws Exception {
        Class<?> clazz = object.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            // Kiểm tra nếu object chứa một method có đính kèm @Sanitizer annotation
            // Thì thực thi hàm đó
            if (method.isAnnotationPresent(Sanitizer.class)) {
                method.setAccessible(true);
                method.invoke(object);
            }
        }
    }

    private void checkEntity(Object object) throws Exception {
        if (Objects.isNull(object)) {
            throw new Exception("The object to serialize is null");
        }

        Class<?> clazz = object.getClass();
        // Kiem tra tinh hop le cua object
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new Exception("The class is not Entity");
        }
    }

    public String exportToJson(Object object) throws Exception {
        Class<?> clazz = object.getClass();
        Map<String, String> jsonMap = new HashMap<>();

        try {
            // Kiểm tra object được xử lý phải là class có chứa Annotation Entity
            checkEntity((object));
            // Chuẩn hoá dữ liệu
            sanitizerObject(object);

            // Khởi tạo cấu trúc map gồm
            // + key: tên thuôc tính trong object,
            // + value: giá trị của thuộc tính trong object
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Column.class)) {
                    jsonMap.put(field.getAnnotation(Column.class).name(), field.get(object).toString());
                }
            }

            // chuyển MAP thành chuỗi json
            String jsonString = jsonMap.entrySet().stream()
                    .map(entry -> "\"" + entry.getKey() + "\":\""
                            + entry.getValue() + "\"")
                    .collect(Collectors.joining(","));
            return "{" + jsonString + "}";

        } catch (Exception e) {
            throw e;
        }
    }
}
