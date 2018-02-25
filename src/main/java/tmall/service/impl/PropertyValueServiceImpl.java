package tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tmall.pojo.Product;
import tmall.pojo.Property;
import tmall.pojo.PropertyValue;
import tmall.service.PropertyService;
import tmall.service.PropertyValueService;

import java.util.List;

@Service
public class PropertyValueServiceImpl extends BaseServiceImpl<PropertyValue> implements PropertyValueService {

    @Autowired
    PropertyService propertyService;

    @Override
    public void init(Product product) {
        List<Property> properties = propertyService.list("category",product.getCategory());
        for(Property property : properties){
            try {
                PropertyValue value = (PropertyValue) list("property", property, "product", product).get(0);
            }catch (Exception e){
                //对应字段为空
                PropertyValue propertyValue = new PropertyValue();
                propertyValue.setProduct(product);
                propertyValue.setProperty(property);
                add(propertyValue);
            }
        }

    }
}